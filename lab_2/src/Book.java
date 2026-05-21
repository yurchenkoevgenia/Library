public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;

    public Book() {
    }

    public Book(String isbn, String title, String author, String genre, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        return "Книга: " + title + ", автор: " + author + ", жанр: " + genre + ", рік: " + publicationYear;
    }
}
