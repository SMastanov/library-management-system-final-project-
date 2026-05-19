package library;

import java.util.ArrayList;
import java.util.List;

public abstract class Member {
    private String memberId;
    private String name;
    private List<LibraryItem> borrowedItems;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public abstract int getMaxBorrowLimit();
    public abstract double getFineMultiplier();
    public abstract String getTierName();

    public void borrowItem(LibraryItem item) {
        if (borrowedItems.size() >= getMaxBorrowLimit()) {
            throw new BorrowLimitExceededException(
                    name + " has reached the maximum borrow limit of " + getMaxBorrowLimit() + " items.");
        }
        if (!item.isAvailable()) {
            throw new ItemNotAvailableException("Item \"" + item.getTitle() + "\" is not available.");
        }
        if (item instanceof Borrowable) {
            ((Borrowable) item).borrow(this);
        }
    }

    public void returnItem(LibraryItem item) {
        if (item instanceof Borrowable) {
            ((Borrowable) item).returnItem(this);
        }
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public List<LibraryItem> getBorrowedItems() { return borrowedItems; }
}
