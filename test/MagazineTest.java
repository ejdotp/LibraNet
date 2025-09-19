import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;

public class MagazineTest {

    private Magazine magazine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        magazine = new Magazine(301, "Tech Weekly", "Various", 154, "media/mags/Tech-Weekly.pdf");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMagazineCreation() {
        assertEquals(301, magazine.getId());
        assertEquals("Tech Weekly", magazine.getTitle());
        assertTrue(magazine.isAvailable());
    }

    @Test
    public void testBorrowMagazine() {
        magazine.borrowItem();
        assertFalse(magazine.isAvailable());
        String expectedOutput = "E-Magazine '" + magazine.getTitle() + "' has been borrowed." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testReturnMagazine() {
        magazine.borrowItem();
        outContent.reset();
        magazine.returnItem();
        assertTrue(magazine.isAvailable());
        String expectedOutput = "E-Magazine '" + magazine.getTitle() + "' has been returned." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testRead() {
        magazine.read();
        String expectedOutput = "Opening e-magazine: " + magazine.getTitle() + System.lineSeparator();
        assertTrue(outContent.toString().startsWith(expectedOutput));
    }

    @Test
    public void testArchiveIssue() {
        magazine.archiveIssue();
        String expectedOutput = "Archiving issue #154 of " + magazine.getTitle() + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}