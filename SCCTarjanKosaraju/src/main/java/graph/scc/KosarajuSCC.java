package graph.scc;

import graph.model.Graph;
import java.util.*;

public class KosarajuSCC implements SCCFinder {
    private final Graph g;

    public KosarajuSCC(Graph g) { this.g = g; }

    @Override
    public List<List<Integer>> findSCC() {
        int n = g.n;
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();
        for (int v = 0; v < n; ++v) dfs1(v, visited, order);

        Graph gr = reverseGraph();
        Arrays.fill(visited, false);
        List<List<Integer>> result = new ArrayList<>();
        Collections.reverse(order);
        for (int v : order)
            if (!visited[v]) {
                List<Integer> comp = new ArrayList<>();
                dfs2(gr, v, visited, comp);
                result.add(comp);
            }
        return result;
    }

    private void dfs1(int v, boolean[] visited, List<Integer> order) {
        if (visited[v]) return;
        visited[v] = true;
        for (var e : g.getEdges(v)) dfs1(e.to, visited, order);
        order.add(v);
    }

    private void dfs2(Graph gr, int v, boolean[] visited, List<Integer> comp) {
        if (visited[v]) return;
        visited[v] = true; comp.add(v);
        for (var e : gr.getEdges(v)) dfs2(gr, e.to, visited, comp);
    }

    private Graph reverseGraph() {
        Graph gr = new Graph(g.n);
        for (int v = 0; v < g.n; v++)
            for (var e : g.getEdges(v))
                gr.addEdge(e.to, e.from, e.weight);
        return gr;
    }
}
