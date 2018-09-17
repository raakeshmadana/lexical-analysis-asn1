package assignment2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DFA {
    List<List<Edge>> dfaAdjList;
    List<Set<Integer>> nfaStates;

    DFA() {
        dfaAdjList = new ArrayList<List<Edge>>();
        nfaStates = new ArrayList<Set<Integer>>();
    }

    public List<List<Edge>> getAdjList() {
        return dfaAdjList;
    }

    public List<Set<Integer>> getNfaStates() {
        return nfaStates;
    }

    public Set<Integer> getNfaStatesFromDfaState(int i) {
        return nfaStates.get(i);
    }

    public int getDfaStateFromNfaStates(Set<Integer> states) {
        for(int i = 0; i < nfaStates.size(); i++) {
            if(nfaStates.get(i).equals(states)) {
                return i;
            }
        }

        return -1;
    }
}