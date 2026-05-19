package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Library {
    private Map<String, LibraryItem> catalog;
    private Map<String, Member> members;

    public Library() {
        this.catalog = new HashMap<>();
        this.members = new HashMap<>();
    }

    public void addItem(LibraryItem item) {
        catalog.put(item.getId(), item);
    }

    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public void borrowItem(String memberId, String itemId) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("  Error: Member with ID \"" + memberId + "\" not found.");
            return;
        }
        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            System.out.println("  Error: Item with ID \"" + itemId + "\" not found.");
            return;
        }
        try {
            member.borrowItem(item);
            System.out.println("  Success: \"" + item.getTitle() + "\" borrowed by " + member.getName() + ".");
            System.out.println("  Due in " + item.getMaxLoanDays() + " days.");
        } catch (ItemNotAvailableException e) {
            System.out.println("  Error: " + e.getMessage());
        } catch (BorrowLimitExceededException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    public void returnItem(String memberId, String itemId, int overdueDays) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("  Error: Member with ID \"" + memberId + "\" not found.");
            return;
        }
        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            System.out.println("  Error: Item with ID \"" + itemId + "\" not found.");
            return;
        }
        if (!member.getBorrowedItems().contains(item)) {
            System.out.println("  Error: " + member.getName() + " has not borrowed this item.");
            return;
        }
        member.returnItem(item);
        System.out.println("  Success: \"" + item.getTitle() + "\" returned by " + member.getName() + ".");

        if (overdueDays > 0 && item instanceof Borrowable) {
            double fine = ((Borrowable) item).calculateFine(overdueDays) * member.getFineMultiplier();
            System.out.printf("  Fine for %d overdue day(s): %.2f AZN%n", overdueDays, fine);
        }
    }

    public SearchResult<LibraryItem> searchByTitle(String keyword) {
        List<LibraryItem> results = catalog.values().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return new SearchResult<>(results, keyword);
    }

    public SearchResult<LibraryItem> searchByAuthor(String keyword) {
        List<LibraryItem> results = catalog.values().stream()
                .filter(item -> item.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return new SearchResult<>(results, keyword);
    }

    public void listAllAvailable() {
        List<LibraryItem> available = catalog.values().stream()
                .filter(LibraryItem::isAvailable)
                .collect(Collectors.toList());
        if (available.isEmpty()) {
            System.out.println("  No items currently available.");
            return;
        }
        System.out.println("  Available items (" + available.size() + "):");
        available.forEach(LibraryItem::displayInfo);
    }

    public void getMemberReport(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("  Error: Member with ID \"" + memberId + "\" not found.");
            return;
        }
        System.out.println("  --- Member Report ---");
        System.out.println("  ID:   " + member.getMemberId());
        System.out.println("  Name: " + member.getName());
        System.out.println("  Tier: " + member.getTierName());
        System.out.println("  Borrow Limit: " + member.getMaxBorrowLimit());
        System.out.println("  Currently Borrowed: " + member.getBorrowedItems().size() + " item(s)");
        if (!member.getBorrowedItems().isEmpty()) {
            for (LibraryItem item : member.getBorrowedItems()) {
                System.out.printf("    - [%s] %s \"%s\"%n", item.getItemType(), item.getId(), item.getTitle());
            }
        }
    }

    public Member getMember(String memberId) { return members.get(memberId); }
    public LibraryItem getItem(String itemId) { return catalog.get(itemId); }
}
