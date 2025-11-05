package graph;
import graph.model.Graph;
import graph.utils.GraphLoader;
import graph.scc.TarjanSCC;
import graph.scc.KosarajuSCC;
import graph.scc.CondensationGraph;
import graph.topo.TopologicalSort;
import graph.dagsp.ShortestPathDAG;
import graph.dagsp.LongestPathDAG;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small1.json");
        int source = 0;
        int target = 5;

        // --- Tarjan SCC ---
        long startTarjan = System.nanoTime();
        List<List<Integer>> sccTarjan = new TarjanSCC(g).findSCC();
        long endTarjan = System.nanoTime();
        System.out.println("SCC Tarjan:");
        int tarjanCycles = 0;
        for (int i = 0; i < sccTarjan.size(); i++) {
            System.out.println("Component " + i + ": " + sccTarjan.get(i) + "  Size: " + sccTarjan.get(i).size());
            if (sccTarjan.get(i).size() > 1) tarjanCycles++;
        }
        double msTarjan = (endTarjan - startTarjan) / 1_000_000.0;
        System.out.println("Tarjan SCC time: " + msTarjan + " ms (" + (endTarjan - startTarjan) + " ns)");
        System.out.println("Tarjan SCC cycles (components >1): " + tarjanCycles);

        // --- Kosaraju SCC ---
        long startKosaraju = System.nanoTime();
        List<List<Integer>> sccKosaraju = new KosarajuSCC(g).findSCC();
        long endKosaraju = System.nanoTime();
        System.out.println("SCC Kosaraju:");
        int kosarajuCycles = 0;
        for (int i = 0; i < sccKosaraju.size(); i++) {
            System.out.println("Component " + i + ": " + sccKosaraju.get(i) + "  Size: " + sccKosaraju.get(i).size());
            if (sccKosaraju.get(i).size() > 1) kosarajuCycles++;
        }
        double msKosaraju = (endKosaraju - startKosaraju) / 1_000_000.0;
        System.out.println("Kosaraju SCC time: " + msKosaraju + " ms (" + (endKosaraju - startKosaraju) + " ns)");
        System.out.println("Kosaraju SCC cycles (components >1): " + kosarajuCycles);

        // --- Condensation graph ---
        CondensationGraph cg = new CondensationGraph(g, sccTarjan);
        System.out.println("Condensation graph edges:");
        for (int cid = 0; cid < cg.condensedAdj.size(); cid++) {
            for (int to : cg.condensedAdj.get(cid)) {
                System.out.println("Component " + cid + " --> Component " + to);
            }
        }

        // --- Topological sort of components ---
        List<Integer> topoComponents = TopologicalSort.kahnOnCondensed(cg.condensedAdj);
        System.out.println("Topological order of components:");
        System.out.println(topoComponents);
        System.out.println("Order of tasks after SCC:");
        for (int cid : topoComponents) {
            System.out.println("Tasks in Component " + cid + ": " + cg.components.get(cid));
        }

        // --- Shortest path ---
        List<Integer> topoOrder = TopologicalSort.kahn(g);
        int[] parentShort = new int[g.n];
        long startShort = System.nanoTime();
        int[] distShort = ShortestPathDAG.shortestPaths(g, source, topoOrder, parentShort);
        long endShort = System.nanoTime();
        List<Integer> pathShort = reconstructPath(parentShort, target);
        double msShort = (endShort - startShort) / 1_000_000.0;
        System.out.println("Shortest path to " + target + ": " + pathShort);
        System.out.println("Length (shortest): " + distShort[target]);
        System.out.println("Shortest path time: " + msShort + " ms (" + (endShort - startShort) + " ns)");

        // --- Longest path ---
        int[] parentLong = new int[g.n];
        long startLong = System.nanoTime();
        int[] distLong = LongestPathDAG.longestPaths(g, source, topoOrder, parentLong);
        long endLong = System.nanoTime();
        List<Integer> pathLong = reconstructPath(parentLong, target);
        double msLong = (endLong - startLong) / 1_000_000.0;
        System.out.println("Longest path to " + target + ": " + pathLong);
        System.out.println("Length (longest/critical): " + distLong[target]);
        System.out.println("Longest path time: " + msLong + " ms (" + (endLong - startLong) + " ns)");
    }

    public static List<Integer> reconstructPath(int[] parent, int target) {
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) path.add(v);
        Collections.reverse(path);
        return path;
    }
}
