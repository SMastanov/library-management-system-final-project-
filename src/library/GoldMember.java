package library;

public class GoldMember extends Member {
    public GoldMember(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxBorrowLimit() { return 6; }

    @Override
    public double getFineMultiplier() { return 2.0; }

    @Override
    public String getTierName() { return "Gold"; }
}
