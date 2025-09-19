import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Library Lib = new Library();
        Scanner scanner = new Scanner(System.in);

        Lib.addItem(new Book(101, "The Hobbit", "J.R.R. Tolkien", 310, "media/books/The-Hobbit.pdf"));
        Lib.addItem(new Audiobook(201, "Dune", "Frank Herbert", 1260, "media/audiobooks/Dune-Audiobook.mp3"));
        Lib.addItem(new Magazine(301, "Tech Weekly", "Various", 154, "media/mags/Tech-Weekly.pdf"));
        System.out.println("\nWelcome to the LibraNet System!");

        while (true) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Display all items");
            System.out.println("2. Borrow an item");
            System.out.println("3. Return an item");
            System.out.println("4. Open/Play an item");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        Lib.displayAllItems();
                        break;
                    case 2:
                        System.out.print("Enter the ID of the item to borrow: ");
                        int borrowId = scanner.nextInt();
                        Lib.borrowItem(borrowId);
                        break;
                    case 3:
                        System.out.print("Enter the ID of the item to return: ");
                        int returnId = scanner.nextInt();
                        Lib.returnItem(returnId);
                        break;
                    case 4:
                        System.out.print("Enter the ID of the item to open/play: ");
                        int openId = scanner.nextInt();
                        LibraryItem itemToOpen = Lib.findItemById(openId);
                        if (itemToOpen != null) {
                            itemToOpen.openMedia();
                        } else {
                            System.out.println("Item with ID " + openId + " not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Thank you for using LibraNet. Exiting.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}