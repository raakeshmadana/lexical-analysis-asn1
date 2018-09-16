package assignment2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ENFA {

    List<List<Edge>> enfaAdjList;
    Map<Integer, String> finalStates = new HashMap<Integer, String>();

    ENFA(List<List<Edge>> enfaAdjList) {
        this.enfaAdjList = enfaAdjList;
    }

    public List<List<Edge>> getAdjList() {
        return enfaAdjList;
    }

    public Map<Integer, String> getFinalStates() {
        return finalStates;
    }

    public void setENFA(List<List<Edge>> enfaAdjList) {
        this.enfaAdjList = enfaAdjList;
    }

    public void setFinalStates(Map<Integer, String> finalStates) {
        this.finalStates = finalStates;
    }

    public void addFinalState(int finalState, String tokenType) {
        finalStates.put(finalState, tokenType);
    }
}