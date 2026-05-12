import java.util.ArrayList;
import java.util.List;

public class Reader {
    private int readerId;
    private String fullName;
    private String phone;
    private List<LibraryOrder> orders;

    public Reader() {
        this.orders = new ArrayList<>();
    }

    public Reader(int readerId, String fullName, String phone) {
        this.readerId = readerId;
        this.fullName = fullName;
        this.phone = phone;
        this.orders = new ArrayList<>();
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<LibraryOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<LibraryOrder> orders) {
        this.orders = orders;
    }

    public List<BookCopy> searchBookByTitle(Catalog catalog, String title) {
        return catalog.searchByTitle(title);
    }

    public void addOrder(LibraryOrder order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        return "Читач: " + fullName + ", телефон: " + phone;
    }
}
