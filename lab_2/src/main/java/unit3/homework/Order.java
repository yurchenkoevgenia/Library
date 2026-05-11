package unit3.homework;

import java.util.Date;
import java.util.List;

public class Order {

    private long id;
    private Date dataCreateOrder;
    private boolean Status;

    private String payment;
    private String delivery;

    private Client client;

    private List<ProductHasOrder> productHasOrderList;

    public List<ProductHasOrder> getProductHasOrderList() {
        return productHasOrderList;
    }

    public Order(long id, Date dataCreateOrder, boolean status, String payment, String delivery, Client client, List<ProductHasOrder> productHasOrderList) {
        this.id = id;
        this.dataCreateOrder = dataCreateOrder;
        Status = status;
        this.payment = payment;
        this.delivery = delivery;
        this.client = client;
        this.productHasOrderList = productHasOrderList;
    }

    public void setProductHasOrderList(List<ProductHasOrder> productHasOrderList) {
        this.productHasOrderList = productHasOrderList;
    }

    public Order() {
    }

    public Order(long id, Date dataCreateOrder, boolean status, String payment, String delivery, Client client) {
        this.id = id;
        this.dataCreateOrder = dataCreateOrder;
        Status = status;
        this.payment = payment;
        this.delivery = delivery;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dataCreateOrder=" + dataCreateOrder +
                ", Status=" + Status +
                ", payment='" + payment + '\'' +
                ", delivery='" + delivery + '\'' +
                ", client=" + client +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataCreateOrder() {
        return dataCreateOrder;
    }

    public void setDataCreateOrder(Date dataCreateOrder) {
        this.dataCreateOrder = dataCreateOrder;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
