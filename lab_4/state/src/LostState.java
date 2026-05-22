public class LostState implements BookState {
    @Override
    public void borrow(LibraryCopy copy, String readerName) {
    }

    @Override
    public void returnCopy(LibraryCopy copy) {
    }

    @Override
    public void markLost(LibraryCopy copy) {
    }

    @Override
    public String getName() {
        return "lost";
    }
}
