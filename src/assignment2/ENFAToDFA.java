package assignment2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ENFAToDFA {
    
    public static Character epsilon = '\u03b5';

    public static Set<Integer> getEClosure(List<List<Edge>> enfaAdjList, Set<Integer> states) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        Set<Integer> eClosure = new HashSet<Integer>();

        // Push all states into stack
        for(int i = 0; i < states.size(); i++) {
            stack.addFirst(states.get(i));
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

        for(int i = 0; i < states.size(); i++) {
            int state = states.get(i);
            List<List<Edge>> edgeList = enfaAdjList.get(state);
            for(int j = 0; j < edgeList.size(); j++) {
                Edge edge = edgeList.get(i);
                if(edge.transition == inputSymbol) {
                    result.add(edge.state);
                }
            }
        }

        return resultantStates;
    }
}