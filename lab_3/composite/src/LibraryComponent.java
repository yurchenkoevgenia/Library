import java.util.List;

public interface LibraryComponent {
    void display(String indent);
    List<BookLeaf> search(String query);
}
