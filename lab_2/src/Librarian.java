public class Librarian {
    private int librarianId;
    private String fullName;
    private int nextOrderId;

    public Librarian() {
        this.nextOrderId = 1;
    }

    public Librarian(int librarianId, String fullName) {
        this.librarianId = librarianId;
        this.fullName = fullName;
        this.nextOrderId = 1;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNextOrderId() {
        return nextOrderId;
    }

    public void setNextOrderId(int nextOrderId) {
        this.nextOrderId = nextOrderId;
    }

    public LibraryOrder issueBook(Reader reader, BookCopy copy, String issueType) {
        if (!copy.isAvailable()) {
            return null;
        }

        copy.setAvailable(false);
        LibraryOrder order = new LibraryOrder(nextOrderId, reader, copy, this, issueType);
        nextOrderId++;
        reader.addOrder(order);
        return order;
    }

    @Override
    public String toString() {
        return "Бібліотекар: " + fullName;
    }
}
