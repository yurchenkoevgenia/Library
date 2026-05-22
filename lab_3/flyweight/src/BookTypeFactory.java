import java.util.HashMap;
import java.util.Map;

public class BookTypeFactory {
    private static final Map<String, BookType> TYPES = new HashMap<>();

    public static BookType getBookType(String title, String author, String genre, int publicationYear) {
        String key = title + "|" + author + "|" + genre + "|" + publicationYear;
        if (!TYPES.containsKey(key)) {
            TYPES.put(key, new BookType(title, author, genre, publicationYear));
        }
        return TYPES.get(key);
    }

    public static int getCachedTypesCount() {
        return TYPES.size();
    }
}
