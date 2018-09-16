package assignment2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class RegexToENFA {

    static List<List<Edge>> enfaAdjList; // ENFA is represented as an Adjacency List
    static ENFA enfa;
    static Deque<Integer> stack = new ArrayDeque<Integer>(); // A stack is maintained to evaluate the postfix regex notation
    static Character epsilon = '\u03b5'; // Represent epsilon transitions
    static Character[][] enfaMatrix;
    static int matrixDimension;
    static SortedSet<Integer> statesWithInTransitions = new TreeSet<Integer>(); // Maintain states with transitions into it
    static int firstState;
    static int initialState;
    static int finalState;

    private static void addState() {
        List<Edge> edgeList = new ArrayList<Edge>();
        enfaAdjList.add(edgeList);
    }

    private static void addTransition(int source, int destination, Character character) {
        List<Edge> edgeList = enfaAdjList.get(source);
        Edge edge = new Edge(destination, character);
        edgeList.add(edge);

        statesWithInTransitions.add(destination);
    }

    private static void basicMachine(Character character) {
        for(int i = 0; i < 2; i++) {
            addState();
        }

        addTransition(enfaAdjList.size() - 2, enfaAdjList.size() - 1, character);

        stack.addFirst(enfaAdjList.size() - 1);
        stack.addFirst(enfaAdjList.size() - 2);
        finalState = enfaAdjList.size() - 1;
    }

    private static void kleeneClosure() {
        int head = stack.removeFirst();
        int tail = stack.removeFirst();

        for(int i = 0; i < 2; i++) {
            addState();
        }

        addTransition(enfaAdjList.size() - 2, head, epsilon);
        addTransition(enfaAdjList.size() - 2, enfaAdjList.size() - 1, epsilon);

        addTransition(tail, head, epsilon);
        addTransition(tail, enfaAdjList.size() - 1, epsilon);

        stack.addFirst(enfaAdjList.size() - 1);
        stack.addFirst(enfaAdjList.size() - 2);
        finalState = enfaAdjList.size() - 1;
    }
    
    private static void concatenation() {
        int head2 = stack.removeFirst();
        int tail2 = stack.removeFirst();
        int head1 = stack.removeFirst();
        int tail1 = stack.removeFirst();

        addTransition(tail1, head2, epsilon);

        stack.addFirst(tail2);
        stack.addFirst(head1);
        finalState = tail2;

    }

    private static void alternation() {
        int head2 = stack.removeFirst();
        int tail2 = stack.removeFirst();
        int head1 = stack.removeFirst();
        int tail1 = stack.removeFirst();

        for(int i = 0; i < 2; i++) {
            addState();
        }

        addTransition(enfaAdjList.size() - 2, head1, epsilon);
        addTransition(enfaAdjList.size() - 2, head2, epsilon);

        addTransition(tail1, enfaAdjList.size() - 1, epsilon);
        addTransition(tail2, enfaAdjList.size() - 1, epsilon);

        stack.addFirst(enfaAdjList.size() - 1);
        stack.addFirst(enfaAdjList.size() - 2);
        finalState = enfaAdjList.size() - 1;
    }

    public static void printAdjList(ENFA combinedEnfa) throws IOException {
        List<List<Edge>> combinedEnfaAdjList = combinedEnfa.getAdjList();
        BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write("\nAdjacency List\n");
        for(int i = 0; i < combinedEnfaAdjList.size(); i++) {
            List<Edge> edgeList = combinedEnfaAdjList.get(i);
            writer.write(i + "\n");
            for(int j = 0; j < edgeList.size(); j++) {
                Edge edge = edgeList.get(j);
                writer.write(edge.transition + " -> " + edge.state + ", ");
            }
            writer.write("\n");
        }

        Map<Integer, String> finalStates = combinedEnfa.getFinalStates();
        for(Map.Entry<Integer, String> entry : finalStates.entrySet()) {
            writer.write(Integer.toString(entry.getKey()) + ": " + entry.getValue() + "\n");
        }

        writer.close();
    }

    private static void createAdjMatrix() {
        matrixDimension = enfaAdjList.size();
        enfaMatrix = new Character[matrixDimension][matrixDimension];

        for(int i = 0; i < matrixDimension; i++) {
            Arrays.fill(enfaMatrix[i], null);
        }

        for(int i = 0; i < enfaAdjList.size(); i++) {
            List<Edge> edgeList = enfaAdjList.get(i);
            for(int j = 0; j < edgeList.size(); j++) {
                Edge edge = edgeList.get(j);
                enfaMatrix[i][edge.state] = edge.transition;
            }
        }
    }

    private static void printAdjMatrix() {
        System.out.println("Adjacency Matrix");

        System.out.print(" \t");
        for(int i = 0; i < matrixDimension; i++) {
            System.out.printf("%4d\t", i);
        }
        System.out.println();
        for(int i = 0; i < matrixDimension; i++) {
            System.out.printf("%4d\t", i);
            for(int j = 0; j < matrixDimension; j++) {
                System.out.printf("%4c\t", enfaMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public static ENFA regexToENFA(ENFA combinedEnfa, String postfixRegex, String tokenType) {
        enfa = combinedEnfa;
        enfaAdjList = enfa.getAdjList();
        firstState = enfaAdjList.size();

        for(int i = 0; i < postfixRegex.length(); i++) {
            char c = postfixRegex.charAt(i);
            switch(c) {
                case '*':
                    kleeneClosure();
                    break;

                case '&':
                    concatenation();
                    break;

                case '|':
                    alternation();
                    break;

                default:
                    basicMachine(c);
            }
        }

        // Find initial and final states by popping the contents of the stack
        if(!stack.isEmpty()) {
            initialState = stack.removeFirst();
            finalState = stack.removeFirst();
        }

        // Combining enfas
        // Add transition from the initial state of the combined enfa to the initial state of the given regular expression
        List<Edge> edgeList = enfaAdjList.get(0);
        Edge edge = new Edge(initialState, epsilon);
        edgeList.add(edge);

        // Keep track of final states in the combined enfa
        enfa.addFinalState(finalState, tokenType);

        return enfa;
    }
}