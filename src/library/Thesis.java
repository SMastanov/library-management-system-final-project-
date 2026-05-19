package library;

public class Thesis extends LibraryItem implements Borrowable {
    public Thesis(String id, String title, String author) {
        super(id, title, author);
    }

    @Override
    public String getItemType() { return "Thesis"; }

    @Override
    public int getMaxLoanDays() { return 21; }

    @Override
    public void borrow(Member member) {
        if (!isAvailable()) {
            throw new ItemNotAvailableException("Thesis \"" + getTitle() + "\" is not available.");
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
