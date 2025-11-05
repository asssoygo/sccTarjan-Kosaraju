package graph.utils;

import graph.model.Graph;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.FileReader;

public class GraphLoader {
    public static Graph loadFromJson(String filename) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader(filename));
        int n = ((Long) obj.get("n")).intValue();
        Graph g = new Graph(n);
        JSONArray edges = (JSONArray) obj.get("edges");
        for (Object o : edges) {
            JSONObject e = (JSONObject) o;
            int u = ((Long) e.get("u")).intValue();
            int v = ((Long) e.get("v")).intValue();
            int w = ((Long) e.get("w")).intValue();
            g.addEdge(u, v, w);
        }
        return g;
    }
}
