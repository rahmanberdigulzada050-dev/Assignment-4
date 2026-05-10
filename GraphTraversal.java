import java.util.*;

public class GraphTraversal {

    private Map<String, List<String>> adjacencyList;
    private Set<String> visitedNodes;

    public GraphTraversal() {
        adjacencyList = new LinkedHashMap<>();
        visitedNodes = new LinkedHashSet<>();
        buildGraph();
    }

    private void buildGraph() {
        adjacencyList.put("A", Arrays.asList("C", "B", "D"));
        adjacencyList.put("B", Arrays.asList("A", "C", "E", "G"));
        adjacencyList.put("C", Arrays.asList("A", "B", "D"));
        adjacencyList.put("D", Arrays.asList("C", "A"));
        adjacencyList.put("E", Arrays.asList("G", "F", "B"));
        adjacencyList.put("F", Arrays.asList("G", "E"));
        adjacencyList.put("G", Arrays.asList("F", "B"));
    }

    // ==================== DEPTH FIRST SEARCH ====================
    public void performDepthFirstSearch(String sourceNode) {
        visitedNodes.clear();
        System.out.print("DFS Traversal Order: ");
        dfsRecursive(sourceNode);
        System.out.println();
    }

    private void dfsRecursive(String currentNode) {
        visitedNodes.add(currentNode);
        System.out.print(currentNode + " ");

        List<String> neighbors = adjacencyList.get(currentNode);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (!visitedNodes.contains(neighbor)) {
                    dfsRecursive(neighbor);
                }
            }
        }
    }

    // ==================== BREADTH FIRST SEARCH ====================
    public void performBreadthFirstSearch(String sourceNode) {
        visitedNodes.clear();
        Queue<String> nodeQueue = new LinkedList<>();

        visitedNodes.add(sourceNode);
        nodeQueue.offer(sourceNode);

        System.out.print("BFS Traversal Order: ");

        while (!nodeQueue.isEmpty()) {
            String currentNode = nodeQueue.poll();
            System.out.print(currentNode + " ");

            List<String> neighbors = adjacencyList.get(currentNode);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visitedNodes.contains(neighbor)) {
                        visitedNodes.add(neighbor);
                        nodeQueue.offer(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GraphTraversal graph = new GraphTraversal();

        System.out.println("=== GRAPH TRAVERSAL DEMONSTRATION ===");
        System.out.println("Graph adjacency list:");
        System.out.println("A: C, B, D");
        System.out.println("B: A, C, E, G");
        System.out.println("C: A, B, D");
        System.out.println("D: C, A");
        System.out.println("E: G, F, B");
        System.out.println("F: G, E");
        System.out.println("G: F, B");
        System.out.println();

        System.out.print("Enter source node for traversal (A-G): ");
        String sourceNode = scanner.next().toUpperCase();

        graph.performDepthFirstSearch(sourceNode);
        graph.performBreadthFirstSearch(sourceNode);

        scanner.close();
    }
}
