import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Library {
    private Map<Integer, LibraryItem> catalog;
    private static final double FINE_PER_DAY = 10.00;

    public Library() {
        this.catalog = new HashMap<>();
    }

    public void addItem(LibraryItem item) {
        if (item != null) {
            catalog.put(item.getId(), item);
            System.out.println("Added to catalog: " + item.getTitle());
        }
    }

    public LibraryItem findItemById(int id) {
        return catalog.get(id);
    }

    public void borrowItem(int id) {
        LibraryItem item = findItemById(id);
        if (item == null) {
            System.out.println("Error: Item with ID " + id + " not found.");
        } else if (!item.isAvailable()) {
            System.out.println("Sorry, '" + item.getTitle() + "' is currently unavailable.");
        } else {
            item.borrowItem();
        }
    }

    public void returnItem(int id) {
        LibraryItem item = findItemById(id);
        if (item == null) {
            System.out.println("Error: Item with ID " + id + " not found.");
        } else if (item.isAvailable()) {
            System.out.println("'" + item.getTitle() + "' is already in the library.");
        } else {
            item.returnItem();
        }
    }

    public void displayAllItems() {
        System.out.println("\n--- Library Catalog Status ---");
        if (catalog.isEmpty()) {
            System.out.println("The library is currently empty.");
            return;
        }
        for (LibraryItem item : catalog.values()) {
            String status = item.isAvailable() ? "Available" : "On Loan (Due: " + item.getDueDate() + ")";
            System.out.println("ID: " + item.getId() + " | Title: " + item.getTitle() + " | Status: " + status);
        }
        System.out.println("----------------------------\n");
    }

    public double calculateFine(int itemId) {
        LibraryItem item = findItemById(itemId);

        if (item == null) {
            System.out.println("Cannot calculate fine: Item with ID " + itemId + " not found.");
            return 0.0;
        }

        if (item.isAvailable() || item.getDueDate() == null) {
            System.out.println("No fine for '" + item.getTitle() + "'. It is currently in the library.");
            return 0.0;
        }

        LocalDate today = LocalDate.now();
        if (today.isAfter(item.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(item.getDueDate(), today);
            double fine = overdueDays * FINE_PER_DAY;
            System.out.println("'" + item.getTitle() + "' is overdue by " + overdueDays + " day(s).");
            return fine;
        } else {
            System.out.println("No fine for '" + item.getTitle() + "'. It is not overdue.");
            return 0.0;
        }
    }

    public Map<Integer, LibraryItem> getCatalog() {
        return this.catalog;
    }
}