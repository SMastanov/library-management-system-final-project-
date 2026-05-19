package library;

import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);
    private static int itemCounter = 1;
    private static int memberCounter = 1;

    public static void main(String[] args) {
        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addNewItem(); break;
                case "2": registerNewMember(); break;
                case "3": borrowItem(); break;
                case "4": returnItem(); break;
                case "5": searchItems(); break;
                case "6": viewMemberReport(); break;
                case "7": viewAllAvailable(); break;
                case "8":
                    running = false;
                    System.out.println("  Goodbye!");
                    break;
                default:
                    System.out.println("  Invalid choice. Please enter a number between 1 and 8.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Library Management System =====");
        System.out.println("1. Add new item        (Book / Magazine / Thesis)");
        System.out.println("2. Register new member");
        System.out.println("3. Borrow item");
        System.out.println("4. Return item");
        System.out.println("5. Search items        (by title or author)");
        System.out.println("6. View member report");
        System.out.println("7. View all available items");
        System.out.println("8. Exit");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void addNewItem() {
        System.out.println("  Item types: 1) Book  2) Magazine  3) Thesis");
        System.out.print("  Select type: ");
        String type = scanner.nextLine().trim();

        System.out.print("  Enter title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("  Error: Title cannot be empty.");
            return;
        }

        System.out.print("  Enter author: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("  Error: Author cannot be empty.");
            return;
        }

        String id = "ITM" + String.format("%03d", itemCounter++);
        LibraryItem item;

        switch (type) {
            case "1": item = new Book(id, title, author); break;
            case "2": item = new Magazine(id, title, author); break;
            case "3": item = new Thesis(id, title, author); break;
            default:
                System.out.println("  Error: Invalid item type.");
                itemCounter--;
                return;
        }

        library.addItem(item);
        System.out.println("  Item added successfully! ID: " + id);
    }

    private static void registerNewMember() {
        System.out.println("  Member tiers: 1) Basic  2) Silver  3) Gold");
        System.out.print("  Select tier: ");
        String tier = scanner.nextLine().trim();

        System.out.print("  Enter member name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("  Error: Name cannot be empty.");
            return;
        }

        String id = "MEM" + String.format("%03d", memberCounter++);
        Member member;

        switch (tier) {
            case "1": member = new BasicMember(id, name); break;
            case "2": member = new SilverMember(id, name); break;
            case "3": member = new GoldMember(id, name); break;
            default:
                System.out.println("  Error: Invalid tier selection.");
                memberCounter--;
                return;
        }

        library.registerMember(member);
        System.out.println("  Member registered! ID: " + id + " | Tier: " + member.getTierName());
    }

    private static void borrowItem() {
        System.out.print("  Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("  Enter item ID: ");
        String itemId = scanner.nextLine().trim();
        library.borrowItem(memberId, itemId);
    }

    private static void returnItem() {
        System.out.print("  Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("  Enter item ID: ");
        String itemId = scanner.nextLine().trim();

        System.out.print("  Enter overdue days (0 if on time): ");
        int overdueDays;
        try {
            overdueDays = Integer.parseInt(scanner.nextLine().trim());
            if (overdueDays < 0) overdueDays = 0;
        } catch (NumberFormatException e) {
            System.out.println("  Invalid number. Assuming 0 overdue days.");
            overdueDays = 0;
        }

        library.returnItem(memberId, itemId, overdueDays);
    }

    private static void searchItems() {
        System.out.println("  Search by: 1) Title  2) Author");
        System.out.print("  Select: ");
        String searchType = scanner.nextLine().trim();

        System.out.print("  Enter keyword: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("  Error: Keyword cannot be empty.");
            return;
        }

        SearchResult<LibraryItem> result;
        switch (searchType) {
            case "1": result = library.searchByTitle(keyword); break;
            case "2": result = library.searchByAuthor(keyword); break;
            default:
                System.out.println("  Invalid search type.");
                return;
        }
        result.display();
    }

    private static void viewMemberReport() {
        System.out.print("  Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        library.getMemberReport(memberId);
    }

    private static void viewAllAvailable() {
        library.listAllAvailable();
    }

    private static void seedSampleData() {
        library.addItem(new Book("ITM001", "Java Programming", "James Gosling"));
        library.addItem(new Book("ITM002", "Clean Code", "Robert Martin"));
        library.addItem(new Magazine("ITM003", "National Geographic", "Various Authors"));
        library.addItem(new Thesis("ITM004", "Machine Learning in Healthcare", "Ali Hasanov"));
        library.addItem(new Book("ITM005", "Design Patterns", "Gang of Four"));
        itemCounter = 6;

        library.registerMember(new BasicMember("MEM001", "Ahmad Mammadov"));
        library.registerMember(new SilverMember("MEM002", "Leyla Aliyeva"));
        library.registerMember(new GoldMember("MEM003", "Orkhan Huseynov"));
        memberCounter = 4;
    }
}
