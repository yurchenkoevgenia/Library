package unit3.homework;

public class BookInOrder {

    private long id;
    private Book book;
    private Order order;
    private int quantity;

    public BookInOrder() {
    }

    public BookInOrder(long id, Book book, Order order, int quantity) {
        this.id = id;
        this.book = book;
        this.order = order;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookInOrder{" +
                "id=" + id +
                ", book=" + book +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }
}
