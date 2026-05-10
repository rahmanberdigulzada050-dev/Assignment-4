import java.util.*;

public class DijkstraShortestPath {

    private Map<String, List<Edge>> roadNetwork;
    private Set<String> visitedCities;
    private Map<String, Integer> shortestDistances;
    private Map<String, String> previousCity;

    static class Edge {
        String destination;
        int distance;

        Edge(String destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    public DijkstraShortestPath() {
        roadNetwork = new HashMap<>();
        buildScottishRoadNetwork();
    }

    private void buildScottishRoadNetwork() {
        addRoad("Edinburgh", "Glasgow", 47);
        addRoad("Edinburgh", "Stirling", 38);
        addRoad("Edinburgh", "Perth", 45);
        addRoad("Glasgow", "Stirling", 26);
        addRoad("Glasgow", "Perth", 58);
        addRoad("Glasgow", "Dundee", 81);
        addRoad("Stirling", "Perth", 43);
        addRoad("Stirling", "Dundee", 69);
        addRoad("Perth", "Dundee", 22);
        // Add reverse roads for undirected graph
        addRoad("Glasgow", "Edinburgh", 47);
        addRoad("Stirling", "Edinburgh", 38);
        addRoad("Perth", "Edinburgh", 45);
        addRoad("Stirling", "Glasgow", 26);
        addRoad("Perth", "Glasgow", 58);
        addRoad("Dundee", "Glasgow", 81);
        addRoad("Perth", "Stirling", 43);
        addRoad("Dundee", "Stirling", 69);
        addRoad("Dundee", "Perth", 22);
    }

    private void addRoad(String fromCity, String toCity, int distanceMiles) {
        roadNetwork.computeIfAbsent(fromCity, k -> new ArrayList<>()).add(new Edge(toCity, distanceMiles));
    }

    public void findShortestPath(String startCity, String endCity) {
        shortestDistances = new HashMap<>();
        previousCity = new HashMap<>();
        visitedCities = new HashSet<>();

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.distance));

        // Initialize distances
        for (String city : roadNetwork.keySet()) {
            shortestDistances.put(city, Integer.MAX_VALUE);
        }
        shortestDistances.put(startCity, 0);

        priorityQueue.offer(new Edge(startCity, 0));

        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            String currentCity = current.destination;

            if (visitedCities.contains(currentCity)) {
                continue;
            }
            visitedCities.add(currentCity);

            List<Edge> neighbors = roadNetwork.get(currentCity);
            if (neighbors != null) {
                for (Edge neighbor : neighbors) {
                    if (!visitedCities.contains(neighbor.destination)) {
                        int newDistance = shortestDistances.get(currentCity) + neighbor.distance;

                        if (newDistance < shortestDistances.get(neighbor.destination)) {
                            shortestDistances.put(neighbor.destination, newDistance);
                            previousCity.put(neighbor.destination, currentCity);
                            priorityQueue.offer(new Edge(neighbor.destination, newDistance));
                        }
                    }
                }
            }
        }

        printShortestPathResult(startCity, endCity);
    }

    private void printShortestPathResult(String startCity, String endCity) {
        System.out.println("\n=== SHORTEST PATH RESULT ===");
        System.out.println("From: " + startCity);
        System.out.println("To: " + endCity);

        if (shortestDistances.get(endCity) == Integer.MAX_VALUE) {
            System.out.println("No path found!");
            return;
        }

        System.out.println("Shortest distance: " + shortestDistances.get(endCity) + " miles");

        // Reconstruct path
        List<String> path = new ArrayList<>();
        String currentCity = endCity;

        while (currentCity != null) {
            path.add(currentCity);
            currentCity = previousCity.get(currentCity);
        }
        Collections.reverse(path);

        System.out.print("Route: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DijkstraShortestPath router = new DijkstraShortestPath();

        System.out.println("=== SCOTTISH ROAD NETWORK SHORTEST PATH ===");
        System.out.println("Available cities: Edinburgh, Glasgow, Stirling, Perth, Dundee");

        System.out.print("Enter starting city: ");
        String startCity = scanner.nextLine().trim();

        System.out.print("Enter destination city: ");
        String endCity = scanner.nextLine().trim();

        router.findShortestPath(startCity, endCity);
        scanner.close();
    }
}