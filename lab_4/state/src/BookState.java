public interface BookState {
    void borrow(LibraryCopy copy, String readerName);
    void returnCopy(LibraryCopy copy);
    void markLost(LibraryCopy copy);
    String getName();
}
