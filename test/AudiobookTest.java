import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;

public class AudiobookTest {

    private Audiobook audiobook;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        audiobook = new Audiobook(201, "Dune", "Frank Herbert", 1260, "media/audiobooks/Dune-Audiobook.mp3");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAudiobookCreation() {
        assertEquals(201, audiobook.getId());
        assertEquals("Dune", audiobook.getTitle());
        assertEquals(1260, audiobook.getDuration());
        assertTrue(audiobook.isAvailable());
    }

    @Test
    public void testBorrowAudiobook() {
        audiobook.borrowItem();
        assertFalse(audiobook.isAvailable());
        String expectedOutput = "Audiobook '" + audiobook.getTitle() + "' has been borrowed." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testReturnAudiobook() {
        audiobook.borrowItem();
        outContent.reset();
        audiobook.returnItem();
        assertTrue(audiobook.isAvailable());
        String expectedOutput = "Audiobook '" + audiobook.getTitle() + "' has been returned." + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testPlay() {
        audiobook.play();
        String expectedOutput = "Playing audiobook: " + audiobook.getTitle() + System.lineSeparator();
        assertTrue(outContent.toString().startsWith(expectedOutput));
    }
}