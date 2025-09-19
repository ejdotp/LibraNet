import java.util.HashMap;
import java.util.Map;

public class Library {
    private Map<Integer, LibraryItem> catalog;

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
            String status = item.isAvailable() ? "Available" : "On Loan";
            System.out.println("ID: " + item.getId() + " | Title: " + item.getTitle() + " | Status: " + status);
        }
        System.out.println("----------------------------\n");
    }
}