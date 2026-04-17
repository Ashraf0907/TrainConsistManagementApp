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
            System.out.println(bogieList.stream().filter(b -> b.capacity > 60).toList());

            // ================= UC9 =================
            System.out.println("\n=== UC9: Grouping ===");
            Map<String, List<Bogie>> grouped =
                    bogieList.stream().collect(Collectors.groupingBy(b -> b.name));
            grouped.forEach((k, v) -> System.out.println(k + " → " + v));

            // ================= UC10 =================
            System.out.println("\n=== UC10: Total Seats ===");
            int total = bogieList.stream().map(b -> b.capacity).reduce(0, Integer::sum);
            System.out.println("Total Seats: " + total);

            // ================= UC11 =================
            System.out.println("\n=== UC11: Regex ===");
            System.out.println(Pattern.matches("TRN-\\d{4}", "TRN-1234"));
            System.out.println(Pattern.matches("PET-[A-Z]{2}", "PET-AB"));

            // ================= UC12 =================
            System.out.println("\n=== UC12: Safety Check ===");
            List<GoodsBogie> goods = new ArrayList<>();
            GoodsBogie g1 = new GoodsBogie("Cylindrical");
            g1.assignCargo("Petroleum");
            GoodsBogie g2 = new GoodsBogie("Open");
            g2.assignCargo("Coal");
            goods.add(g1);
            goods.add(g2);

            boolean safe = goods.stream().allMatch(b ->
                    !b.type.equals("Cylindrical") || b.cargo.equals("Petroleum"));
            System.out.println(safe ? "SAFE" : "NOT SAFE");

            // ================= UC13 =================
            System.out.println("\n=== UC13: Performance ===");
            List<Bogie> bigList = new ArrayList<>();
            for (int i = 0; i < 10000; i++)
                bigList.add(new Bogie("Sleeper", (i % 100) + 1));

            long t1 = System.nanoTime();
            for (Bogie b : bigList) if (b.capacity > 60) {}
            long t2 = System.nanoTime();

            long t3 = System.nanoTime();
            bigList.stream().filter(b -> b.capacity > 60).toList();
            long t4 = System.nanoTime();

            System.out.println("Loop: " + (t2 - t1));
            System.out.println("Stream: " + (t4 - t3));

            // ================= UC14 =================
            System.out.println("\n=== UC14: Exception ===");
            Bogie valid = new Bogie("Sleeper", 72);
            System.out.println(valid);

            Bogie invalid = new Bogie("AC", -10); // throws

        } catch (InvalidCapacityException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ================= UC15 =================
        System.out.println("\n=== UC15: Safe Cargo Assignment ===");
        GoodsBogie g1 = new GoodsBogie("Cylindrical");
        GoodsBogie g2 = new GoodsBogie("Rectangular");

        try {
            g1.assignCargo("Petroleum");
            g2.assignCargo("Petroleum");
        } catch (CargoSafetyException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Process completed");
        }

        // ================= UC16 =================
        System.out.println("\n=== UC16: Bubble Sort ===");
        int[] arr = {72, 56, 24, 70, 60};
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
        System.out.println(Arrays.toString(arr));

        // ================= UC17 =================
        System.out.println("\n=== UC17: Arrays.sort ===");
        String[] names = {"Sleeper", "AC Chair", "First Class", "General"};
        Arrays.sort(names);
        System.out.println(Arrays.toString(names));

        // ================= UC18 =================
        System.out.println("\n=== UC18: Linear Search ===");
        String[] ids = {"BG101", "BG205", "BG309"};
        String key = "BG205";
        boolean found = false;
        for (String id : ids) {
            if (id.equals(key)) {
                found = true;
                break;
            }
        }
        System.out.println(found ? "Found" : "Not Found");

        // ================= UC19 =================
        System.out.println("\n=== UC19: Binary Search ===");
        Arrays.sort(ids);
        int low = 0, high = ids.length - 1;
        found = false;

        while (low <= high) {
            int mid = (low + high) / 2;
            int res = key.compareTo(ids[mid]);

            if (res == 0) {
                found = true;
                break;
            } else if (res < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println(found ? "Found" : "Not Found");

        // ================= UC20 =================
        System.out.println("\n=== UC20: Fail Fast ===");
        String[] empty = {};

        if (empty.length == 0) {
            throw new IllegalStateException("Cannot search: No bogies available");
        }
    }
}