public class AvailableState implements BookState {
    @Override
    public void borrow(LibraryCopy copy, String readerName) {
        copy.setReaderName(readerName);
        copy.setState(new IssuedState());
    }

    @Override
    public void returnCopy(LibraryCopy copy) {
    }

    @Override
    public void markLost(LibraryCopy copy) {
        copy.setState(new LostState());
    }

    @Override
    public String getName() {
        return "available";
    }
}
