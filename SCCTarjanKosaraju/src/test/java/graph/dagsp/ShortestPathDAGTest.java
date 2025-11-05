package graph.dagsp;

import graph.model.Graph;
import graph.utils.GraphLoader;
import graph.topo.TopologicalSort;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ShortestPathDAGTest {
    @Test
    void pathCalculation() throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small1.json");
        List<Integer> topo = TopologicalSort.kahn(g);
        int[] dist = ShortestPathDAG.shortestPaths(g, 0, topo);
        assertTrue(dist[5] > 0);
    }
}
