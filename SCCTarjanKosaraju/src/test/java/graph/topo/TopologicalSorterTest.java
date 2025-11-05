package graph.topo;

import graph.model.Graph;
import graph.utils.GraphLoader;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopologicalSorterTest {
    @Test
    void dagIsOrdered() throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small1.json");
        List<Integer> order = TopologicalSort.kahn(g);
        assertEquals(g.n, order.size());
    }

    @Test
    void dagHasNoCycles() throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small1.json");
        assertDoesNotThrow(() -> TopologicalSort.kahn(g));
    }
}
