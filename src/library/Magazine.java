package library;

public class Magazine extends LibraryItem implements Borrowable {
    public Magazine(String id, String title, String author) {
        super(id, title, author);
    }

    @Override
    public String getItemType() { return "Magazine"; }

    @Override
    public int getMaxLoanDays() { return 7; }

    @Override
    public void borrow(Member member) {
        if (!isAvailable()) {
            throw new ItemNotAvailableException("Magazine \"" + getTitle() + "\" is not available.");
        }
        setAvailable(false);
        member.getBorrowedItems().add(this);
    }

    @Override
    public void returnItem(Member member) {
        setAvailable(true);
        member.getBorrowedItems().remove(this);
    }

    @Override
    public double calculateFine(int overdueDays) {
        if (overdueDays <= 0) return 0;
        return overdueDays * 0.25;
    }
}
