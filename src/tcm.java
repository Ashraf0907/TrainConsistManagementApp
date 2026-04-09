import java.util.*;
public class tcm {
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
    }
}