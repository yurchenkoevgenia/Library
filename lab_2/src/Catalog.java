import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private String name;
    private List<BookCopy> bookCopies;

    public Catalog() {
        this.bookCopies = new ArrayList<>();
    }

    public Catalog(String name) {
        this.name = name;
        this.bookCopies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public void addBookCopy(BookCopy copy) {
        bookCopies.add(copy);
    }

    public List<BookCopy> searchByTitle(String title) {
        List<BookCopy> result = new ArrayList<>();
        for (BookCopy copy : bookCopies) {
            if (copy.getBook().getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(copy);
            }
        }
        return result;
    }

    public List<BookCopy> getAvailableCopies() {
        List<BookCopy> result = new ArrayList<>();
        for (BookCopy copy : bookCopies) {
            if (copy.isAvailable()) {
                result.add(copy);
            }
        }
        return result;
    }
}
