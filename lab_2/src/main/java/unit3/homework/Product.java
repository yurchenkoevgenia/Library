package unit3.homework;

import java.math.BigDecimal;
import java.util.List;

public class Product {

    private long id;
    private String name;
    private double price;
    private BigDecimal price2;
    private String description;

    private List<ProductHasOrder> productHasOrders;

    public List<ProductHasOrder> getProductHasOrders() {
        return productHasOrders;
    }

    public void setProductHasOrders(List<ProductHasOrder> productHasOrders) {
        this.productHasOrders = productHasOrders;
    }

    public Product(long id, String name, double price, BigDecimal price2, String description, List<ProductHasOrder> productHasOrders, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.price2 = price2;
        this.description = description;
        this.productHasOrders = productHasOrders;
        this.category = category;
    }

    private Category category;

    public Product() {
    }

    public Product(long id, String name, double price, BigDecimal price2, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.price2 = price2;
        this.description = description;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category=" + category +
                '}';
    }
}
