package graph.dagsp;

import graph.model.Graph;
import java.util.*;

public class ShortestPathDAG {
    // Возвращает массив длин и заполняет parent для восстановления пути
    public static int[] shortestPaths(Graph g, int source, List<Integer> topoOrder, int[] parent) {
        int n = g.n;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;
        for (int v : topoOrder)
            for (var e : g.getEdges(v))
                if (dist[v] != Integer.MAX_VALUE && dist[e.to] > dist[v] + e.weight) {
                    dist[e.to] = dist[v] + e.weight;
                    parent[e.to] = v;
                }
        return dist;
    }
}
