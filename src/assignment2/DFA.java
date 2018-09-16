package assignment2;

class DFA {
    List<List<Edge>> DFAAdjList;
    List<List<Integer>> NFAStates;


    public List<List<Edge>> getAdjList() {
        return DFAAdjList;
    }

    public List<List<Integer>> getNFAStates() {
        return NFAStates;
    }

    public List<Integer> correspondingNFAStates(int i) {
        return NFAStates.get(i);
    }
}