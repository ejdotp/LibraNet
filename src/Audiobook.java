import java.time.LocalDate;

public class Audiobook extends LibraryItem implements Playable {
    private int durationInMinutes;

    public Audiobook(int itemId, String title, String author, int durationInMinutes, String filePath) {
        super(itemId, title, author, filePath);
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public void play() {
        System.out.println("Playing audiobook: " + this.title);
        openMedia();
    }

    @Override
    public int getDuration() {
        return this.durationInMinutes;
    }

    @Override
    public void borrowItem() {
        this.isAvailable = false;
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(BORROWING_PERIOD_DAYS);
        System.out.println("Audiobook '" + title + "' has been borrowed. Due Date: " + this.dueDate);
    }

    @Override
    public void returnItem() {
        this.isAvailable = true;
        this.borrowDate = null;
        this.dueDate = null;
        System.out.println("Audiobook '" + title + "' has been returned.");
    }
}