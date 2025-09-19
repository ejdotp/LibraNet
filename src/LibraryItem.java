import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

public abstract class LibraryItem {
    protected int id;
    protected String title;
    protected String author;
    protected boolean isAvailable;
    protected String filePath;

    public LibraryItem(int id, String title, String author, String filePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.filePath = filePath;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public abstract void borrowItem();

    public abstract void returnItem();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void openMedia() {
        if (!Desktop.isDesktopSupported()) {
            System.err.println("Desktop API is not supported on this system.");
            return;
        }

        try {
            File file = new File(this.filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.err.println("File not found at path: " + this.filePath);
            }
        } catch (IOException e) {
            System.err.println("Error opening the file: " + e.getMessage());
        }
    }
}
