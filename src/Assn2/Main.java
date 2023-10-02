package Assn2;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Double> nodeList = new HashMap<>();
        nodeList.put("S", 24.0);
        nodeList.put("A", 21.0);
        nodeList.put("B", 19.0);
        nodeList.put("C", 19.0);
        nodeList.put("D", 9.0);
        nodeList.put("E", 11.0);
        nodeList.put("F", 12.0);
        nodeList.put("G", 4.0);
        nodeList.put("H", 6.0);
        nodeList.put("Z", 0.0);

        Map<String, Map<String, Double>> edgeList = new HashMap<>();
        Map<String, Double> mapS = new HashMap<>();
        mapS.put("A", 3.0); mapS.put("B", 9.0); mapS.put("C", 4.0);
        edgeList.put("S", mapS);
        Map<String, Double> mapA = new HashMap<>();
        mapA.put("C", 2.0);
        edgeList.put("A", mapA);
        Map<String, Double> mapB = new HashMap<>();
        mapB.put("C", 13.0);
        edgeList.put("B", mapB);
        Map<String, Double> mapC = new HashMap<>();
        mapC.put("D", 5.0); mapC.put("E", 4.0); mapC.put("F", 8.0);
        edgeList.put("C", mapC);
        Map<String, Double> mapD = new HashMap<>();
        mapD.put("F", 5.0);
        edgeList.put("D", mapD);
        Map<String, Double> mapE = new HashMap<>();
        mapE.put("F", 7.0);
        edgeList.put("E", mapE);
        Map<String, Double> mapF = new HashMap<>();
        mapF.put("G", 8.0); mapF.put("H", 7.0); mapF.put("Z", 18.0);
        edgeList.put("F", mapF);
        Map<String, Double> mapG = new HashMap<>();
        mapG.put("Z", 9.0);
        edgeList.put("G", mapG);
        Map<String, Double> mapH = new HashMap<>();
        mapH.put("Z", 6.0);
        edgeList.put("H", mapH);
        edgeList.put("Z", new HashMap<>());

        SearchStrategies searchStrategies = new SearchStrategies();
        searchStrategies.DFS(nodeList, edgeList, "S", "Z");
        searchStrategies.BFS(nodeList, edgeList, "S", "Z");
        searchStrategies.IDS(nodeList, edgeList, "S", "Z");
        searchStrategies.LCFS(nodeList, edgeList, "S", "Z");
        searchStrategies.BestFS(nodeList, edgeList, "S", "Z");
        searchStrategies.AStar(nodeList, edgeList, "S", "Z");
        searchStrategies.BandB(nodeList, edgeList, "S", "Z");
    }
}