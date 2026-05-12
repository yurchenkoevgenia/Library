public class Main {
    public static void main(String[] args) {
        // Створюємо один примірник книги і змінюємо його стан.
        LibraryCopy copy = new LibraryCopy(1, "Kobzar");

        System.out.println(copy);
        copy.borrow("Maria Ivanenko");
        System.out.println(copy);
        copy.returnCopy();
        System.out.println(copy);
        copy.borrow("Oksana Petrenko");
        copy.markLost();
        System.out.println(copy);
    }
}
