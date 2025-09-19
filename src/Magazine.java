public class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(int itemId, String title, String author, int issueNumber, String filePath) {
        super(itemId, title, author, filePath);
        this.issueNumber = issueNumber;
    }

    public void archiveIssue() {
        System.out.println("Archiving issue #" + this.issueNumber + " of " + this.title);
    }

    public void read() {
        System.out.println("Opening e-magazine: " + this.title);
        openMedia();
    }

    @Override
    public void borrowItem() {
        this.isAvailable = false;
        System.out.println("E-Magazine '" + title + "' has been borrowed.");
    }

    @Override
    public void returnItem() {
        this.isAvailable = true;
        System.out.println("E-Magazine '" + title + "' has been returned.");
    }
}