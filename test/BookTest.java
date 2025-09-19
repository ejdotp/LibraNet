import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;

public class BookTest {

    private Book book;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        book = new Book(101, "Meow Book", "Me. Owmeow", 45, "media/books/Meow-Book.pdf");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testBookCreation() {
        assertEquals(101, book.getId());
        assertEquals("Meow Book", book.getTitle());
        assertEquals(45, book.getPageCount());
        assertTrue(book.isAvailable());
    }

    @Test
    public void testBorrowBook() {
        book.borrowItem();
        assertFalse(book.isAvailable());
        String expectedOutput = book.getTitle() + " has been borrowed." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testReturnBook() {
        book.borrowItem();
        outContent.reset();
        book.returnItem();
        assertTrue(book.isAvailable());
        String expectedOutput = book.getTitle() + " has been returned." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testRead() {
        book.read();
        String expectedOutput = "Opening book: " + book.getTitle() + System.lineSeparator();
        assertTrue(outContent.toString().startsWith(expectedOutput));
    }
}