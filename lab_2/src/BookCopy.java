public class BookCopy {
    private int copyId;
    private Book book;
    private boolean available;
    private String shelfLocation;

    public BookCopy() {
    }

    public BookCopy(int copyId, Book book, boolean available, String shelfLocation) {
        this.copyId = copyId;
        this.book = book;
        this.available = available;
        this.shelfLocation = shelfLocation;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    @Override
    public String toString() {
        String status = available ? "доступний" : "виданий";
        return "Екземпляр №" + copyId + " — " + book.getTitle() + " (" + status + ", полиця: " + shelfLocation + ")";
    }
}
