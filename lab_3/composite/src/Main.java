import java.util.List;

public class Main {
    public static void main(String[] args) {
        CatalogComposite root = new CatalogComposite("Library Catalog");

        CatalogComposite poetry = new CatalogComposite("Poetry");
        poetry.add(new BookLeaf("Kobzar", "Taras Shevchenko", "Poetry", 1840));
        poetry.add(new BookLeaf("Moysej", "Ivan Franko", "Poetry", 1905));

        CatalogComposite fiction = new CatalogComposite("Fiction");
        fiction.add(new BookLeaf("Zahar Berkut", "Ivan Franko", "Novel", 1883));
        fiction.add(new BookLeaf("Forest Song", "Lesya Ukrainka", "Drama", 1911));

        root.add(poetry);
        root.add(fiction);

        System.out.println("Catalog tree:");
        root.display("");

        System.out.println();
        System.out.println("Search result for 'Franko':");
        List<BookLeaf> found = root.search("Franko");
        for (BookLeaf book : found) {
            book.display("");
        }
    }
}
