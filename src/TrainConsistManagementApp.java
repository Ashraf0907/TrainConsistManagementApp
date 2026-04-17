import java.util.*;
import java.util.stream.Collectors;
public class TrainConsistManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App ===");
        List<String> bogies = new ArrayList<>();
        System.out.println("Initial number of bogies: " + bogies.size());
        System.out.println("Train initialized successfully.");
        bogies.add("Sleeper");
        bogies.add("AC Chair");
        bogies.add("First Class");
        System.out.println("\nAfter Adding Bogies:");
        System.out.println(bogies);
        bogies.remove("AC Chair");
        System.out.println("\nAfter Removing AC Chair:");
        System.out.println(bogies);
        if (bogies.contains("Sleeper")) {
            System.out.println("\nSleeper bogie exists in the train.");
        } else {
            System.out.println("\nSleeper bogie not found.");
        }
        System.out.println("\nFinal Bogie List:");
        System.out.println(bogies);
        Set<String> bogieIDs = new HashSet<>();
        bogieIDs.add("BG101");
        bogieIDs.add("BG102");
        bogieIDs.add("BG103");
        bogieIDs.add("BG101");
        bogieIDs.add("BG102");
        System.out.println("\nUnique Bogie IDs:");
        System.out.println(bogieIDs);
        LinkedList<String> train = new LinkedList<>();
        train.add("Engine");
        train.add("Sleeper");
        train.add("AC");
        train.add("Cargo");
        train.add("Guard");
        System.out.println("\nInitial Train Consist:");
        System.out.println(train);
        train.add(2, "Pantry");
        System.out.println("\nAfter Adding Pantry at Position 2:");
        System.out.println(train);
        train.removeFirst();
        train.removeLast();
        System.out.println("\nAfter Removing First and Last Bogies:");
        System.out.println(train);
        LinkedHashSet<String> formation = new LinkedHashSet<>();

        // Step 1: Add bogies
        formation.add("Engine");
        formation.add("Sleeper");
        formation.add("Cargo");
        formation.add("Guard");

        // Step 2: Add duplicate
        formation.add("Sleeper"); // duplicate ignored

        // Step 3: Display formation
        System.out.println("\nFinal Train Formation (Ordered & Unique):");
        System.out.println(formation);HashMap<String, Integer> capacityMap = new HashMap<>();

        // Step 1: Add bogie-capacity mapping
        capacityMap.put("Sleeper", 72);
        capacityMap.put("AC Chair", 50);
        capacityMap.put("First Class", 24);

        // Step 2: Display using entrySet()
        System.out.println("\nBogie Capacity Details:");

        for (Map.Entry<String, Integer> entry : capacityMap.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue() + " seats");
            // ================= UC8: Stream Filtering =================
            System.out.println("\n=== UC8: Filter Passenger Bogies (Capacity > 60) ===");

            class Bogie {
                String name;
                int capacity;

                Bogie(String name, int capacity) {
                    this.name = name;
                    this.capacity = capacity;
                }

                public String toString() {
                    return name + " (" + capacity + ")";
                }
            }

// Create bogie list with capacity
            List<Bogie> bogieList = new ArrayList<>();
            bogieList.add(new Bogie("Sleeper", 72));
            bogieList.add(new Bogie("AC Chair", 50));
            bogieList.add(new Bogie("First Class", 24));
            bogieList.add(new Bogie("General", 90));

// Display original list
            System.out.println("Original Bogies:");
            System.out.println(bogieList);

// Apply stream filter
            List<Bogie> filteredBogies = bogieList.stream()
                    .filter(b -> b.capacity > 60)
                    .toList();

// Display filtered result
            System.out.println("\nFiltered Bogies (Capacity > 60):");
            System.out.println(filteredBogies);
            System.out.println("UC8 executed successfully");
            Map<String, List<Bogie>> groupedBogies = bogieList.stream()
                    .collect(Collectors.groupingBy(b -> b.name));
        }
    }
}