import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.*;

// ================= CUSTOM EXCEPTION =================
class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String message) {
        super(message);
    }
}

// ================= BOGIE CLASS =================
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

    GoodsBogie(String type, String cargo) {
        this.type = type;
        this.cargo = cargo;
    }
}

// ================= MAIN CLASS =================
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
            System.out.println("\n=== UC11: Regex Validation ===");
            String trainId = "TRN-1234";
            String cargo = "PET-AB";

            if (Pattern.matches("TRN-\\d{4}", trainId))
                System.out.println("Valid Train ID");
            else
                System.out.println("Invalid Train ID");

            if (Pattern.matches("PET-[A-Z]{2}", cargo))
                System.out.println("Valid Cargo Code");
            else
                System.out.println("Invalid Cargo Code");

            // ================= UC12 =================
            System.out.println("\n=== UC12: Safety Check ===");
            List<GoodsBogie> goods = List.of(
                    new GoodsBogie("Cylindrical", "Petroleum"),
                    new GoodsBogie("Open", "Coal"),
                    new GoodsBogie("Box", "Grain")
            );

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

            System.out.println("Loop: " + (t2 - t1));
            System.out.println("Stream: " + (t4 - t3));

            // ================= UC14 =================
            System.out.println("\n=== UC14: Exception Handling ===");

            Bogie valid = new Bogie("Sleeper", 72);
            System.out.println(valid);

            Bogie invalid = new Bogie("AC", -10); // throws exception

        } catch (InvalidCapacityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}