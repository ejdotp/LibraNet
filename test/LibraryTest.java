import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    private Library library;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        library = new Library();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddItemAndFindById() {
        Book book = new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf");
        library.addItem(book);
        LibraryItem foundItem = library.findItemById(101);
        assertNotNull(foundItem, "Item should not be null.");
        assertEquals("Meow Book", foundItem.getTitle(), "The title should match.");
    }

    @Test
    public void testBorrowAvailableItem() {
        Book book = new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf");
        library.addItem(book);
        library.borrowItem(101);
        assertFalse(book.isAvailable(), "Book should be marked as unavailable after borrowing.");
    }

    @Test
    public void testReturnItem() {
        Book book = new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf");
        library.addItem(book);
        library.borrowItem(101);
        library.returnItem(101);
        assertTrue(book.isAvailable(), "Book should be marked as available after returning.");
    }

    @Test
    public void testFindNonExistentItem() {
        LibraryItem foundItem = library.findItemById(999);
        assertNull(foundItem, "Finding a non-existent item should return null.");
    }

    @Test
    public void testBorrowUnavailableItem() {
        Book book = new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf");
        library.addItem(book);
        library.borrowItem(101);
        outContent.reset();
        library.borrowItem(101);
        String expectedOutput = "Sorry, 'Meow Book' is currently unavailable." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        assertFalse(book.isAvailable(), "Book should remain unavailable.");
    }

    @Test
    public void testBorrowNonExistentItem() {
        library.borrowItem(999);
        String expectedOutput = "Error: Item with ID 999 not found." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testReturnAvailableItem() {
        Book book = new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf");
        library.addItem(book);
        outContent.reset();
        library.returnItem(101);
        String expectedOutput = "'" + book.getTitle() + "' is already in the library." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        assertTrue(book.isAvailable(), "Book should remain available.");
    }

    @Test
    public void testReturnNonExistentItem() {
        library.returnItem(999);
        String expectedOutput = "Error: Item with ID 999 not found." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testDisplayAllItems() {
        library.addItem(new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf"));
        library.addItem(new Audiobook(201, "Harry Potter", "J.K. Rowling", 1320, "media/audiobooks/Harry-Potter.mp3"));
        library.borrowItem(101);
        outContent.reset();

        library.displayAllItems();

        String output = outContent.toString().replace("\r\n", "\n");
        assertTrue(output.contains("--- Library Catalog Status ---"));
        assertTrue(output.contains("ID: 101 | Title: Meow Book | Status: On Loan"));
        assertTrue(output.contains("ID: 201 | Title: Harry Potter | Status: Available"));
        assertTrue(output.contains("----------------------------"));
    }

    @Test
    public void testDisplayEmptyLibrary() {
        library.displayAllItems();
        String expectedOutput = "\n--- Library Catalog Status ---\n" + "The library is currently empty.\n";
        assertEquals(expectedOutput, outContent.toString().replace("\r\n", "\n"));
    }
}