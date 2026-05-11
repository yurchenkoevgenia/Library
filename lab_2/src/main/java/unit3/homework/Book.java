package unit3.homework;

import java.util.List;

public class Book {

    private long id;
    private String title;
    private String author;
    private String description;
    private boolean availability;
    private int copies;

    private Category category;
    private List<BookInOrder> bookInOrderList;

    public Book() {
    }

    public Book(long id, String title, String author, String description, boolean availability, int copies, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.availability = availability;
        this.copies = copies;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<BookInOrder> getBookInOrderList() {
        return bookInOrderList;
    }

    public void setBookInOrderList(List<BookInOrder> bookInOrderList) {
        this.bookInOrderList = bookInOrderList;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", availability=" + availability +
                ", copies=" + copies +
                ", category=" + category +
                '}';
    }
}
