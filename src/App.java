import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {
    public static void main(String[] args) {
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);

        lib.addItem(new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf"));
        lib.addItem(new Audiobook(201, "Harry Potter", "J.K. Rowling", 1320, "media/audiobooks/Harry-Potter.mp3"));
        lib.addItem(new Magazine(301, "Dog World", "Various", 102, "media/mags/Dog-World.pdf"));
        System.out.println("\nWelcome to the LibraNet System!");

        while (true) {
            System.out.println("\n=============== MAIN MENU ===============");
            System.out.println("1. List Items by Type (and Open)");
            System.out.println("2. Display All Items");
            System.out.println("3. Borrow an Item");
            System.out.println("4. Return an Item");
            System.out.println("5. Calculate Fine");
            System.out.println("6. Exit");
            System.out.println("=========================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        listItemAndOpen(lib, sc);
                        break;
                    case 2:
                        lib.displayAllItems();
                        break;
                    case 3:
                        System.out.print("Enter the ID of the item to borrow: ");
                        int borrowId = sc.nextInt();
                        lib.borrowItem(borrowId);
                        break;
                    case 4:
                        System.out.print("Enter the ID of the item to return: ");
                        int returnId = sc.nextInt();
                        lib.returnItem(returnId);
                        break;
                    case 5:
                        System.out.print("Enter the ID of the item to check fine for: ");
                        int fineId = sc.nextInt();
                        double fineAmount = lib.calculateFine(fineId);
                        System.out.printf("Total fine for item %d is: %.2f%n", fineId, fineAmount);
                        break;
                    case 6:
                        System.out.println("Thank you for using LibraNet. Exiting.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void listItemAndOpen(Library library, Scanner sc) {
        System.out.println("\n--- List Items by Type ---");
        System.out.println("1. Books");
        System.out.println("2. Audiobooks");
        System.out.println("3. E-Magazines");
        System.out.print("Select item type to list: ");

        try {
            int typeChoice = sc.nextInt();
            Class<?> itemType;
            String typeName;

            switch (typeChoice) {
                case 1:
                    itemType = Book.class;
                    typeName = "Books";
                    break;
                case 2:
                    itemType = Audiobook.class;
                    typeName = "Audiobooks";
                    break;
                case 3:
                    itemType = Magazine.class;
                    typeName = "E-Magazines";
                    break;
                default:
                    System.out.println("Invalid type choice.");
                    return;
            }

            System.out.println("\n--- Available " + typeName + " ---");
            AtomicBoolean itemFound = new AtomicBoolean(false);

            library.getCatalog().values().stream()
                    .filter(item -> itemType.isInstance(item))
                    .forEach(item -> {
                        String status = item.isAvailable() ? "Available" : "On Loan";
                        System.out.println(
                                "ID: " + item.getId() + " | Title: " + item.getTitle() + " | Status: " + status);
                        itemFound.set(true);
                    });

            if (!itemFound.get()) {
                System.out.println("No items of this type found in the library.");
                return;
            }

            System.out.print("\nWould you like to open an item from this list? (y/n): ");
            String openChoice = sc.next();

            if (openChoice.equalsIgnoreCase("y")) {
                System.out.print("Enter the ID of the item to open: ");
                int openId = sc.nextInt();
                LibraryItem itemToOpen = library.findItemById(openId);

                if (itemToOpen != null && itemType.isInstance(itemToOpen)) {
                    itemToOpen.openMedia();
                } else if (itemToOpen == null) {
                    System.out.println("Item with ID " + openId + " not found.");
                } else {
                    System.out.println("Error: Item " + openId + " is not a " + typeName + ".");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
        }
    }
}