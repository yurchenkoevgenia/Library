public class BookType {
    private final String title;
    private final String author;
    private final String genre;
    private final int publicationYear;

    public BookType(String title, String author, String genre, int publicationYear) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public String toString() {
        return title + " | " + author + " | " + genre + " | " + publicationYear;
    }
}
