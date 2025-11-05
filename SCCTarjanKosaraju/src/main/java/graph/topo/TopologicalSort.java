package graph.topo;

import graph.model.Graph;
import java.util.*;

public class TopologicalSort {
    public static List<Integer> kahn(Graph g) {
        int n = g.n;
        int[] indeg = new int[n];
        for (int v = 0; v < n; v++)
            for (var e : g.getEdges(v)) indeg[e.to]++;
        Queue<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < n; v++) if (indeg[v] == 0) q.add(v);
        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            result.add(u);
            for (var e : g.getEdges(u))
                if (--indeg[e.to] == 0) q.add(e.to);
        }
        if (result.size() != n) throw new RuntimeException("Cycle detected!");
        return result;
    }

    public static List<Integer> kahnOnCondensed(List<Set<Integer>> condensedAdj) {
        int n = condensedAdj.size();
        int[] indeg = new int[n];
        for (int v = 0; v < n; v++)
            for (int to : condensedAdj.get(v)) indeg[to]++;
        Queue<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < n; v++) if (indeg[v] == 0) q.add(v);
        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            result.add(u);
            for (int to : condensedAdj.get(u))
                if (--indeg[to] == 0) q.add(to);
        }
        if (result.size() != n) throw new RuntimeException("Cycle detected in condensed graph!");
        return result;
    }
}
