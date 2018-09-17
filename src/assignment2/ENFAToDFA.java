package assignment2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class ENFAToDFA {
    
    public static Character epsilon = '\u03b5';

    public static Set<Integer> getEClosure(List<List<Edge>> enfaAdjList, Set<Integer> states) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        Set<Integer> eClosure = new HashSet<Integer>();
        
        // Push all states into stack
        Iterator iterator = states.iterator();
        while(iterator.hasNext()) {
            stack.addFirst((Integer)iterator.next());
        }

        while(!stack.isEmpty()) {
            int state = stack.removeFirst();
            List<Edge> edgeList = enfaAdjList.get(state);
            for(int i = 0; i < edgeList.size(); i++) {
                Edge edge = edgeList.get(i);
                if(edge.transition == epsilon) {
                    if(!eClosure.contains(edge.state)) {
                        eClosure.add(edge.state);
                        stack.addFirst(edge.state);
                    }
                }
            }
        }

        return eClosure;
    }

    public static Set<Integer> move(List<List<Edge>> enfaAdjList, Set<Integer> states, Character inputSymbol) {
        Set<Integer> resultantStates = new HashSet<Integer>();

        Iterator iterator = states.iterator();
        while(iterator.hasNext()) {
            int state = (Integer)iterator.next();
            List<Edge> edgeList = enfaAdjList.get(state);
            for(int i = 0; i < edgeList.size(); i++) {
                Edge edge = edgeList.get(i);
                if(edge.transition == inputSymbol) {
                    resultantStates.add(edge.state);
                }
            }
        }

        return resultantStates;
    }

    public static DFA toDFA(ENFA enfa, List<Character> inputSymbols) {

        DFA dfa = new DFA();
        // Add the first state to the DFA
        List<List<Edge>> dfaAdjList = dfa.getAdjList();
        List<Edge> edgeList = new ArrayList<Edge>();
        dfaAdjList.add(edgeList);

        // Add the corresponding NFA states
        List<Set<Integer>> nfaStates = dfa.getNfaStates();
        Set<Integer> states = new HashSet<Integer>();
        states.add(0);
        List<List<Edge>> enfaAdjList = enfa.getAdjList();
        nfaStates.add(getEClosure(enfaAdjList, states)); // e-closure of the start state of the ENFA is the first state of the DFA

        // Use stack to "mark" states
        Deque<Integer> stack = new ArrayDeque<Integer>();

        // Add the only state of the DFA to the stack
        stack.addFirst(0);

        while(!stack.isEmpty()) { // While there is an unmarked state in the DFA
            // Mark the state T
            int currentDfaState = stack.removeFirst();

            for(int i = 0; i < inputSymbols.size(); i++) { // For all input symbols of the alphabet
                Character c = inputSymbols.get(i);

                // Compute the e-closure of move(T, c)
                states = dfa.getNfaStatesFromDfaState(currentDfaState);
                Set<Integer> resultantStates = move(enfaAdjList, states, c);
                Set<Integer> eClosure = getEClosure(enfaAdjList, resultantStates);

                // If the computed e-closure is not already in the DFA, add it
                if(!dfa.containsNfaStates(eClosure)) {
                    edgeList = new ArrayList<Edge>();
                    dfaAdjList.add(edgeList);
                    nfaStates.add(eClosure);

                    stack.addFirst(dfaAdjList.size());
                }

                // Add the transition from T to U on c
                int destinationDfaState = dfa.getDfaStateFromNfaStates(eClosure);
                edgeList = dfaAdjList.get(currentDfaState);
                Edge edge = new Edge(destinationDfaState, epsilon);
                edgeList.add(edge);
            }

        }

        return dfa;
    }

    public static void printAdjList(DFA dfa) throws IOException {
        List<List<Edge>> dfaAdjList = dfa.getAdjList();
        BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write("\nAdjacency List\n");
        for(int i = 0; i < dfaAdjList.size(); i++) {
            List<Edge> edgeList = dfaAdjList.get(i);
            writer.write(i + "\n");
            for(int j = 0; j < edgeList.size(); j++) {
                Edge edge = edgeList.get(j);
                writer.write(edge.transition + " -> " + edge.state + ", ");
            }
            writer.write("\n");
        }

        writer.close();
    }
}