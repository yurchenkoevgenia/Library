import java.util.List;

public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog("Каталог міської бібліотеки");

        Book book1 = new Book("978-966-10-1111-1", "Кобзар", "Тарас Шевченко", "Поезія", 1840);
        Book book2 = new Book("978-966-10-2222-2", "Захар Беркут", "Іван Франко", "Повість", 1883);

        BookCopy copy1 = new BookCopy(101, book1, true, "A-1");
        BookCopy copy2 = new BookCopy(102, book1, true, "A-2");
        BookCopy copy3 = new BookCopy(201, book2, true, "B-1");

        catalog.addBookCopy(copy1);
        catalog.addBookCopy(copy2);
        catalog.addBookCopy(copy3);

        Reader reader = new Reader(1, "Іваненко Марія", "+380501112233");
        Librarian librarian = new Librarian(1, "Петренко Олена");

        System.out.println("Пошук книги у каталозі за назвою 'Кобзар':");
        List<BookCopy> foundBooks = reader.searchBookByTitle(catalog, "Кобзар");
        for (BookCopy copy : foundBooks) {
            System.out.println(copy);
        }

        System.out.println("\nВидача книг читачеві:");
        LibraryOrder order1 = librarian.issueBook(reader, copy1, "абонемент");
        LibraryOrder order2 = librarian.issueBook(reader, copy3, "читальний зал");

        if (order1 != null) {
            System.out.println(order1);
        }
        if (order2 != null) {
            System.out.println(order2);
        }

        System.out.println("\nСписок замовлень читача:");
        for (LibraryOrder order : reader.getOrders()) {
            System.out.println(order);
        }

        System.out.println("\nДоступні екземпляри після видачі:");
        for (BookCopy copy : catalog.getAvailableCopies()) {
            System.out.println(copy);
        }
    }
}
