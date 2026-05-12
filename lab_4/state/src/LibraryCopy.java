public class LibraryCopy {
    private final int copyId;
    private final String title;
    private String readerName;
    private BookState state;

    public LibraryCopy(int copyId, String title) {
        this.copyId = copyId;
        this.title = title;
        this.state = new AvailableState();
    }

    public int getCopyId() {
        return copyId;
    }

    public String getTitle() {
        return title;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getStateName() {
        return state.getName();
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public void borrow(String readerName) {
        state.borrow(this, readerName);
    }

    public void returnCopy() {
        state.returnCopy(this);
    }

    public void markLost() {
        state.markLost(this);
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "copyId=" + copyId +
                ", title='" + title + '\'' +
                ", readerName='" + readerName + '\'' +
                ", state=" + state.getName() +
                '}';
    }
}
