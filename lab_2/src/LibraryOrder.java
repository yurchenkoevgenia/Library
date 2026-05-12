import java.time.LocalDate;

public class LibraryOrder {
    private int orderId;
    private Reader reader;
    private BookCopy bookCopy;
    private Librarian librarian;
    private String issueType;
    private LocalDate orderDate;

    public LibraryOrder() {
    }

    public LibraryOrder(int orderId, Reader reader, BookCopy bookCopy, Librarian librarian, String issueType) {
        this.orderId = orderId;
        this.reader = reader;
        this.bookCopy = bookCopy;
        this.librarian = librarian;
        this.issueType = issueType;
        this.orderDate = LocalDate.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Замовлення №" + orderId + ": " + reader.getFullName()
                + " отримав(ла) книгу \"" + bookCopy.getBook().getTitle() + "\" — "
                + issueType + ", бібліотекар: " + librarian.getFullName()
                + ", дата: " + orderDate;
    }
}
