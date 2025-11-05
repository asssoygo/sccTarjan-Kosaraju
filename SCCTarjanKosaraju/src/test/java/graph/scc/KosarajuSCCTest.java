package graph.scc;

import graph.model.Graph;
import graph.utils.GraphLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KosarajuSCCTest {
    @Test
    void small2() throws Exception {
        Graph g = GraphLoader.loadFromJson("src/main/resources/data/small2.json");
        var scc = new KosarajuSCC(g).findSCC();
        // В этом графе есть одна компонентa минимум из 3 вершин
        boolean foundBig = false;
        for (var comp : scc) if (comp.size() > 2) foundBig = true;
        assertTrue(foundBig);
    }
}
