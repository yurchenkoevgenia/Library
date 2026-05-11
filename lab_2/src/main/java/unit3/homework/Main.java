package unit3.homework;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");
        category.setDescription("Books about programming and software development");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setDescription("A handbook of agile software craftsmanship");
        book.setAvailability(true);
        book.setCopies(3);
        book.setCategory(category);

        Reader reader = new Reader();
        reader.setId(1L);
        reader.setFirstName("Zhenya");
        reader.setLastName("Yurchenko");
        reader.setEmail("zhenya.yurchenko.14@gmail.com");
        reader.setPhone("+380501234567");

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(new Date());
        order.setStatus(true);
        order.setPlaceType("abonement");
        order.setReader(reader);

        BookInOrder bookInOrder = new BookInOrder();
        bookInOrder.setId(1L);
        bookInOrder.setBook(book);
        bookInOrder.setOrder(order);
        bookInOrder.setQuantity(1);

        category.setBookList(List.of(book));
        book.setBookInOrderList(List.of(bookInOrder));
        reader.setOrderList(List.of(order));
        order.setBookInOrderList(List.of(bookInOrder));

        System.out.println(category);
        System.out.println(book);
        System.out.println(reader);
        System.out.println(order);
        System.out.println(bookInOrder);
    }
}
