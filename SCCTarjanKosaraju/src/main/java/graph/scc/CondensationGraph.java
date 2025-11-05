package graph.scc;

import graph.model.Graph;
import java.util.*;

public class CondensationGraph {
    public int[] componentId;
    public List<List<Integer>> components;
    public List<Set<Integer>> condensedAdj;

    public CondensationGraph(Graph g, List<List<Integer>> scc) {
        int n = g.n;
        componentId = new int[n];
        for (int cid = 0; cid < scc.size(); cid++) {
            for (int v : scc.get(cid)) componentId[v] = cid;
        }
        components = scc;
        condensedAdj = new ArrayList<>();
        for (int cid = 0; cid < scc.size(); cid++) condensedAdj.add(new HashSet<>());
        for (int v = 0; v < n; v++) {
            int fromC = componentId[v];
            for (var e : g.getEdges(v)) {
                int toC = componentId[e.to];
                if (fromC != toC) condensedAdj.get(fromC).add(toC);
            }
        }
    }
}
