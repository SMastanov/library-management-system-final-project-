package library;

public class SilverMember extends Member {
    public SilverMember(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxBorrowLimit() { return 4; }

    @Override
    public double getFineMultiplier() { return 1.5; }

    @Override
    public String getTierName() { return "Silver"; }
}
