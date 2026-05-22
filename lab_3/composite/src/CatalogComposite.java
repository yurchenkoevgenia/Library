import java.util.ArrayList;
import java.util.List;

public class CatalogComposite implements LibraryComponent {
    private final String name;
    private final List<LibraryComponent> children;

    public CatalogComposite(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void add(LibraryComponent component) {
        children.add(component);
    }

    public void remove(LibraryComponent component) {
        children.remove(component);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + name);
        for (LibraryComponent child : children) {
            child.display(indent + "  ");
        }
    }

    @Override
    public List<BookLeaf> search(String query) {
        List<BookLeaf> result = new ArrayList<>();
        for (LibraryComponent child : children) {
            result.addAll(child.search(query));
        }
        return result;
    }
}
