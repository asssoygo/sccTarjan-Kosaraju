package graph.scc;

import graph.model.Graph;
import java.util.*;

public class TarjanSCC implements SCCFinder {
    private final Graph g;
    private int index = 0;
    private int[] idx, lowlink;
    private boolean[] onStack;
    private Deque<Integer> stack;
    private List<List<Integer>> result;

    public TarjanSCC(Graph g) { this.g = g; }

    @Override
    public List<List<Integer>> findSCC() {
        int n = g.n;
        idx = new int[n];
        lowlink = new int[n];
        onStack = new boolean[n];
        Arrays.fill(idx, -1);
        stack = new ArrayDeque<>();
        result = new ArrayList<>();
        for (int v = 0; v < n; ++v) if (idx[v] == -1) dfs(v);
        return result;
    }

    private void dfs(int v) {
        idx[v] = lowlink[v] = index++;
        stack.push(v); onStack[v] = true;
        for (var edge : g.getEdges(v)) {
            int w = edge.to;
            if (idx[w] == -1) {
                dfs(w);
                lowlink[v] = Math.min(lowlink[v], lowlink[w]);
            } else if (onStack[w]) {
                lowlink[v] = Math.min(lowlink[v], idx[w]);
            }
        }
        if (lowlink[v] == idx[v]) {
            List<Integer> scc = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack[w] = false;
                scc.add(w);
            } while (w != v);
            result.add(scc);
        }
    }
}
