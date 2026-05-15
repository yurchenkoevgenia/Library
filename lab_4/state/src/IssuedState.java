public class IssuedState implements BookState {
    @Override
    public void borrow(LibraryCopy copy, String readerName) {
    }

    @Override
    public void returnCopy(LibraryCopy copy) {
        copy.setReaderName(null);
        copy.setState(new AvailableState());
    }

    @Override
    public void markLost(LibraryCopy copy) {
        copy.setState(new LostState());
    }

    @Override
    public String getName() {
        return "issued";
    }
}
