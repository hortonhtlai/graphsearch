import javafx.util.Pair;
import java.util.*;

public class SearchStrategies {
    private String searchStrategy;
    private LinkedList<Pair<List<String>, Double>> frontier;
    private int idsDepth;

    private void frontierAdd(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                             Pair<List<String>, Double> currPath, String nextNode) {
        if (currPath == null) {
            currPath = new Pair<>(new ArrayList<>(), null);
        }
        List<String> currPathNodes = currPath.getKey();
        Double currPathValue = currPath.getValue();

        List<String> nextPathNodes = new ArrayList<>(currPathNodes);
        nextPathNodes.add(nextNode);

        String lastNode;
        if (currPathNodes.isEmpty()) {
            lastNode = null;
        } else {
            lastNode = currPathNodes.get(currPathNodes.size() - 1);
        }

        if (searchStrategy.equals("DFS") || searchStrategy.equals("BFS")) {
            frontier.addLast(new Pair<>(nextPathNodes, null));
        } else if (searchStrategy.equals("IDS") && currPathNodes.size() <= idsDepth + 1) {
            frontier.addLast(new Pair<>(nextPathNodes, null));
        } else if (searchStrategy.equals("LCFS")) {
            if (lastNode == null) {
                frontier.addLast(new Pair<>(nextPathNodes, 0.0));
            } else {
                frontier.addLast(new Pair<>(nextPathNodes, currPathValue + edgeList.get(lastNode).get(nextNode)));
            }
            frontier.sort(Comparator.comparing(Pair::getValue));
        } else if (searchStrategy.equals("BestFS")) {
            frontier.addLast(new Pair<>(nextPathNodes, nodeList.get(nextNode)));
            frontier.sort(Comparator.comparing(Pair::getValue));
        } else if (searchStrategy.equals("AStar")) {
            if (lastNode == null) {
                frontier.addLast(new Pair<>(nextPathNodes, nodeList.get(nextNode)));
            } else {
                frontier.addLast(new Pair<>(nextPathNodes, currPathValue - nodeList.get(lastNode) +
                        edgeList.get(lastNode).get(nextNode) + nodeList.get(nextNode)));
            }
            frontier.sort(Comparator.comparing(Pair::getValue));
        } else if (searchStrategy.equals("BandB")) {
            if (lastNode == null) {
                frontier.addLast(new Pair<>(nextPathNodes, nodeList.get(nextNode)));
            } else {
                frontier.addLast(new Pair<>(nextPathNodes, currPathValue - nodeList.get(lastNode) +
                        edgeList.get(lastNode).get(nextNode) + nodeList.get(nextNode)));
            }
        }
    }

    private Pair<List<String>, Double> frontierRemove() {
        if (searchStrategy.equals("DFS") || searchStrategy.equals("IDS") || searchStrategy.equals("BandB")) {
            return frontier.removeLast();
        } else if (searchStrategy.equals("BFS") || searchStrategy.equals("LCFS") ||
                searchStrategy.equals("BestFS") || searchStrategy.equals("AStar")) {
            return frontier.removeFirst();
        }
        return null;
    }

    private void printPathNodes(List<String> pathNodes) {
        System.out.print("\nReturned path: ");
        for (String s : pathNodes) System.out.print(s + " ");
        System.out.println("\n");
    }

    private List<String> search(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                               String startNode, String goalNode) {
        frontierAdd(nodeList, edgeList, null, startNode);
        while (!frontier.isEmpty()) {
            Pair<List<String>, Double> currPath = frontierRemove();
            List<String> currPathNodes = currPath.getKey();
            String lastNode = currPathNodes.get(currPathNodes.size() - 1);
            System.out.print(lastNode + " ");
            if (lastNode.equals(goalNode)) {
                printPathNodes(currPathNodes);
                return currPathNodes;
            }
            for (String nextNode : edgeList.get(lastNode).keySet()) {
                frontierAdd(nodeList, edgeList, currPath, nextNode);
            }
        }
        return null;
    }

    public List<String> DFS(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                            String startNode, String goalNode) {
        searchStrategy = "DFS";
        frontier = new LinkedList<>();
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");
        return search(nodeList, edgeList, startNode, goalNode);
    }

    public List<String> BFS(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                            String startNode, String goalNode) {
        searchStrategy = "BFS";
        frontier = new LinkedList<>();
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");
        return search(nodeList, edgeList, startNode, goalNode);
    }

    public List<String> IDS(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                            String startNode, String goalNode) {
        searchStrategy = "IDS";
        frontier = new LinkedList<>();
        idsDepth = 1;
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");
        List<String> retPathNodes = null;
        while (retPathNodes == null) {
            retPathNodes = search(nodeList, edgeList, startNode, goalNode);
            idsDepth++;
        }
        return retPathNodes;
    }

    public List<String> LCFS(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                            String startNode, String goalNode) {
        searchStrategy = "LCFS";
        frontier = new LinkedList<>();
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");
        return search(nodeList, edgeList, startNode, goalNode);
    }

    public List<String> BestFS(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                             String startNode, String goalNode) {
        searchStrategy = "BestFS";
        frontier = new LinkedList<>();
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");
        return search(nodeList, edgeList, startNode, goalNode);
    }

    public List<String> AStar(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                               String startNode, String goalNode) {
        searchStrategy = "AStar";
        frontier = new LinkedList<>();
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");
        return search(nodeList, edgeList, startNode, goalNode);
    }

    public List<String> BandB(Map<String, Double> nodeList, Map<String, Map<String, Double>> edgeList,
                                String startNode, String goalNode) {
        searchStrategy = "BandB";
        frontier = new LinkedList<>();
        System.out.print("Search strategy: " + searchStrategy + "\nExpanded nodes: ");

        frontierAdd(nodeList, edgeList, null, startNode);
        Double upperBoundPathValue = Double.MAX_VALUE;
        List<String> upperBoundPathNodes = null;
        while (!frontier.isEmpty()) {
            Pair<List<String>, Double> currPath = frontierRemove();
            Double currPathValue = currPath.getValue();
            if (currPathValue < upperBoundPathValue) {
                List<String> currPathNodes = currPath.getKey();
                String lastNode = currPathNodes.get(currPathNodes.size() - 1);
                System.out.print(lastNode + " ");
                if (lastNode.equals(goalNode)) {
                    upperBoundPathValue = currPathValue;
                    upperBoundPathNodes = currPathNodes;
                }
                for (String nextNode : edgeList.get(lastNode).keySet()) {
                    frontierAdd(nodeList, edgeList, currPath, nextNode);
                }
            }
        }
        printPathNodes(upperBoundPathNodes);
        return upperBoundPathNodes;
    }
}
