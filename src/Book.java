import java.time.LocalDate;

public class Book extends LibraryItem {
    private int pageCount;

    public Book(int itemId, String title, String author, int pageCount, String filePath) {
        super(itemId, title, author, filePath);
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void read() {
        System.out.println("Opening book: " + this.title);
        openMedia();
    }

    @Override
    public void borrowItem() {
        this.isAvailable = false;
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(BORROWING_PERIOD_DAYS);
        System.out.println("Book '" + title + "' has been borrowed. Due Date: " + this.dueDate);
    }

    @Override
    public void returnItem() {
        this.isAvailable = true;
        this.borrowDate = null;
        this.dueDate = null;
        System.out.println("Book '" + title + "' has been returned.");
    }
}