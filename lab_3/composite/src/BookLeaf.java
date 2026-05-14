import java.util.ArrayList;
import java.util.List;

public class BookLeaf implements LibraryComponent {
    private final String title;
    private final String author;
    private final String genre;
    private final int publicationYear;

    public BookLeaf(String title, String author, String genre, int publicationYear) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + title + " | " + author + " | " + genre + " | " + publicationYear);
    }

    @Override
    public List<BookLeaf> search(String query) {
        String normalized = query.toLowerCase();
        List<BookLeaf> result = new ArrayList<>();
        if (title.toLowerCase().contains(normalized)
                || author.toLowerCase().contains(normalized)
                || genre.toLowerCase().contains(normalized)) {
            result.add(this);
        }
        return result;
    }
}
