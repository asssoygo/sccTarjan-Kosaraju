package graph.scc;

import graph.model.Graph;
import graph.utils.GraphLoader;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TarjanSCCTest {
    @Test
    void small1() throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small1.json");
        List<List<Integer>> scc = new TarjanSCC(g).findSCC();
        assertFalse(scc.isEmpty());
    }

    @Test
    void allSingleSCC() throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small1.json");
        List<List<Integer>> scc = new TarjanSCC(g).findSCC();
        for (List<Integer> c : scc) assertEquals(1, c.size());
    }
}
