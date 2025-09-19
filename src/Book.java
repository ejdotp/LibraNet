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
        System.out.println(title + " has been borrowed.");
    }

    @Override
    public void returnItem() {
        this.isAvailable = true;
        System.out.println(title + " has been returned.");
    }
}