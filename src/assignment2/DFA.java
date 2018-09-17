package assignment2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class DFA {
    List<Map<Character, Integer>> dfaAdjList;
    List<Set<Integer>> nfaStates;
    Map<Integer, String> finalStates;

    DFA() {
        dfaAdjList = new ArrayList<Map<Character, Integer>>();
        nfaStates = new ArrayList<Set<Integer>>();
        finalStates = new HashMap<Integer, String>();
    }

    public List<Map<Character, Integer>> getAdjList() {
        return dfaAdjList;
    }

    public List<Set<Integer>> getNfaStates() {
        return nfaStates;
    }

    public Map<Integer, String> getFinalStates() {
        return finalStates;
    }

    public boolean containsNfaStates(Set<Integer> states) {
        for(int i = 0; i < nfaStates.size(); i++) {
            if(states.equals(nfaStates.get(i))) {
                return true;
            }
        }

        return false;
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

    public void printNfaStates() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt", true));
        writer.write("\nNFA states\n");

        for(int i = 0; i < nfaStates.size(); i++) {
            Set<Integer> states = nfaStates.get(i);
            Iterator iterator = states.iterator();
            while(iterator.hasNext()) {
                writer.write(Integer.toString((Integer)iterator.next()) + " ");
            }
            writer.write("\n");
        }

        writer.close();
    }
}