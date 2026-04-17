import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.*;

// ================= CUSTOM EXCEPTIONS =================
class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String msg) {
        super(msg);
    }
}

class CargoSafetyException extends RuntimeException {
    public CargoSafetyException(String msg) {
        super(msg);
    }
}

// ================= BOGIE =================
class Bogie {
    String name;
    int capacity;

    Bogie(String name, int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String toString() {
        return name + " (" + capacity + ")";
    }
}

// ================= GOODS BOGIE =================
class GoodsBogie {
    String type;
    String cargo;

    GoodsBogie(String type) {
        this.type = type;
    }

    void assignCargo(String cargo) {
        if (type.equals("Rectangular") && cargo.equals("Petroleum")) {
            throw new CargoSafetyException("Unsafe: Petroleum cannot be assigned to Rectangular bogie");
        }
        this.cargo = cargo;
        System.out.println("Assigned: " + type + " → " + cargo);
    }
}

// ================= MAIN =================
public class TrainConsistManagementApp {

    public static void main(String[] args) {

        System.out.println("=== Train Consist Management App ===");

        try {
            // ================= UC8 =================
            List<Bogie> bogieList = new ArrayList<>();
            bogieList.add(new Bogie("Sleeper", 72));
            bogieList.add(new Bogie("AC Chair", 50));
            bogieList.add(new Bogie("First Class", 24));
            bogieList.add(new Bogie("General", 90));

            System.out.println("\n=== UC8: Filter ===");
            List<Bogie> filtered = bogieList.stream()
                    .filter(b -> b.capacity > 60)
                    .toList();
            System.out.println(filtered);

            // ================= UC9 =================
            System.out.println("\n=== UC9: Grouping ===");
            Map<String, List<Bogie>> grouped =
                    bogieList.stream().collect(Collectors.groupingBy(b -> b.name));
            grouped.forEach((k, v) -> System.out.println(k + " → " + v));

            // ================= UC10 =================
            System.out.println("\n=== UC10: Total Seats ===");
            int total = bogieList.stream()
                    .map(b -> b.capacity)
                    .reduce(0, Integer::sum);
            System.out.println("Total Seats: " + total);

            // ================= UC11 =================
            System.out.println("\n=== UC11: Regex ===");
            String trainId = "TRN-1234";
            String cargoCode = "PET-AB";

            System.out.println(Pattern.matches("TRN-\\d{4}", trainId) ? "Valid Train ID" : "Invalid Train ID");
            System.out.println(Pattern.matches("PET-[A-Z]{2}", cargoCode) ? "Valid Cargo Code" : "Invalid Cargo Code");

            // ================= UC12 =================
            System.out.println("\n=== UC12: Safety Check ===");
            List<GoodsBogie> goods = new ArrayList<>();

            GoodsBogie g1 = new GoodsBogie("Cylindrical");
            g1.assignCargo("Petroleum");

            GoodsBogie g2 = new GoodsBogie("Open");
            g2.assignCargo("Coal");

            GoodsBogie g3 = new GoodsBogie("Box");
            g3.assignCargo("Grain");

            goods.add(g1);
            goods.add(g2);
            goods.add(g3);

            boolean safe = goods.stream().allMatch(b ->
                    !b.type.equals("Cylindrical") || b.cargo.equals("Petroleum"));

            System.out.println(safe ? "SAFE" : "NOT SAFE");

            // ================= UC13 =================
            System.out.println("\n=== UC13: Performance ===");

            List<Bogie> bigList = new ArrayList<>();
            for (int i = 0; i < 100000; i++)
                bigList.add(new Bogie("Sleeper", (i % 100) + 1));

            long t1 = System.nanoTime();
            List<Bogie> loop = new ArrayList<>();
            for (Bogie b : bigList)
                if (b.capacity > 60) loop.add(b);
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            List<Bogie> stream = bigList.stream()
                    .filter(b -> b.capacity > 60)
                    .toList();
            long t4 = System.nanoTime();

            System.out.println("Loop Time: " + (t2 - t1));
            System.out.println("Stream Time: " + (t4 - t3));

            // ================= UC14 =================
            System.out.println("\n=== UC14: Exception Handling ===");

            Bogie valid = new Bogie("Sleeper", 72);
            System.out.println(valid);

            Bogie invalid = new Bogie("AC", -10); // throws exception

        } catch (InvalidCapacityException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ================= UC15 =================
        System.out.println("\n=== UC15: Safe Cargo Assignment ===");

        GoodsBogie g1 = new GoodsBogie("Cylindrical");
        GoodsBogie g2 = new GoodsBogie("Rectangular");

        try {
            g1.assignCargo("Petroleum");   // safe
            g2.assignCargo("Petroleum");   // unsafe
        } catch (CargoSafetyException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Process completed");
        }

        System.out.println("Program continues safely...");
        // ================= UC16: Bubble Sort =================
        System.out.println("\n=== UC16: Bubble Sort (Passenger Capacities) ===");

// Step 1: Create array
        int[] capacities = {72, 56, 24, 70, 60};

// Step 2: Bubble Sort logic
        for (int i = 0; i < capacities.length - 1; i++) {
            for (int j = 0; j < capacities.length - i - 1; j++) {

                if (capacities[j] > capacities[j + 1]) {
                    // swap
                    int temp = capacities[j];
                    capacities[j] = capacities[j + 1];
                    capacities[j + 1] = temp;
                }
            }
        }

// Step 3: Display sorted array
        System.out.println("Sorted Capacities:");
        for (int cap : capacities) {
            System.out.print(cap + " ");
        }
        System.out.println();
    }
}