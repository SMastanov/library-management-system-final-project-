package library;

public class BasicMember extends Member {
    public BasicMember(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxBorrowLimit() { return 2; }

    @Override
    public double getFineMultiplier() { return 1.0; }

    @Override
    public String getTierName() { return "Basic"; }
}
