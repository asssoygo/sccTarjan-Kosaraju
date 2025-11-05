package graph.model;

import java.util.*;

public class Graph {
    public final int n;
    public final List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int from, int to, int weight) {
        adj.get(from).add(new Edge(from, to, weight));
    }

    public List<Edge> getEdges(int v) { return adj.get(v); }
}
