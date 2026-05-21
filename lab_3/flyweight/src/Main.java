import java.util.List;

public class Main {
    public static void main(String[] args) {
        BookType kobzar = BookTypeFactory.getBookType("Kobzar", "Taras Shevchenko", "Poetry", 1840);
        BookType zahar = BookTypeFactory.getBookType("Zahar Berkut", "Ivan Franko", "Novel", 1883);

        List<BookCopy> copies = List.of(
                new BookCopy(101, "A-1", true, kobzar),
                new BookCopy(102, "A-2", true, kobzar),
                new BookCopy(201, "B-1", true, zahar),
                new BookCopy(202, "B-2", false, zahar),
                new BookCopy(203, "B-3", true, zahar)
        );

        for (BookCopy copy : copies) {
            System.out.println(copy);
        }

        System.out.println("Shared types: " + BookTypeFactory.getCachedTypesCount());
    }
}
