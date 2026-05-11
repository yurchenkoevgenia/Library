package unit3.homework;

import java.util.Date;
import java.util.List;

public class Order {

    private long id;
    private Date orderDate;
    private boolean status;
    private String placeType;

    private Reader reader;
    private List<BookInOrder> bookInOrderList;

    public Order() {
    }

    public Order(long id, Date orderDate, boolean status, String placeType, Reader reader) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.placeType = placeType;
        this.reader = reader;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public List<BookInOrder> getBookInOrderList() {
        return bookInOrderList;
    }

    public void setBookInOrderList(List<BookInOrder> bookInOrderList) {
        this.bookInOrderList = bookInOrderList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", placeType='" + placeType + '\'' +
                ", reader=" + reader +
                '}';
    }
}
