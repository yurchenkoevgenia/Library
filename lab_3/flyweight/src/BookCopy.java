public class BookCopy {
    private final int copyId;
    private final String shelfLocation;
    private boolean available;
    private final BookType bookType;

    public BookCopy(int copyId, String shelfLocation, boolean available, BookType bookType) {
        this.copyId = copyId;
        this.shelfLocation = shelfLocation;
        this.available = available;
        this.bookType = bookType;
    }

    public int getCopyId() {
        return copyId;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BookType getBookType() {
        return bookType;
    }

    @Override
    public String toString() {
        String status = available ? "available" : "issued";
        return "Copy " + copyId + " [" + shelfLocation + ", " + status + "] -> " + bookType;
    }
}
