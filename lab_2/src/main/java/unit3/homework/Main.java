package unit3.homework;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Category category = new Category();
        category.setId(1L);
        category.setName("Macbook Air");
        category.setDescription("Найбільш тонкий і легкий ноутбук Apple");
        category.setImage("/image/air.jpg");

        Product product = new Product();
        product.setId(1L);
        product.setName("Macbook Air M1 256");
        product.setPrice(42000);
        product.setPrice2(BigDecimal.valueOf(42000));
        product.setDescription("Завдяки чипу Apple M1 найбільш тонкий і легкий ноутбук Apple зазнав вражаючих змін. Центральний процесор відтепер працює до 3,5 раза швидше. Графічний — до 5 разів.");
        product.setCategory(category);

        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Oksana");
        client.setLastName("Ivanenko");
        client.setAge(20);
        client.setEmail("oksana@example.com");
        client.setPhone("+380501112233");
        client.setAddress("Kyiv");

        Order order = new Order();
        order.setId(1L);
        order.setDataCreateOrder(new Date());
        order.setStatus(true);
        order.setPayment("Card");
        order.setDelivery("Nova Poshta");
        order.setClient(client);

        ProductHasOrder productHasOrder = new ProductHasOrder();
        productHasOrder.setId(1L);
        productHasOrder.setProduct(product);
        productHasOrder.setOrder(order);
        productHasOrder.setQuantity(1);

        category.setProduct(product);
        category.setProductList(List.of(product));
        product.setProductHasOrders(List.of(productHasOrder));
        client.setOrderList(List.of(order));
        order.setProductHasOrderList(List.of(productHasOrder));

        System.out.println(category);
        System.out.println(product);
        System.out.println(client);
        System.out.println(order);
        System.out.println(productHasOrder);
    }
}
