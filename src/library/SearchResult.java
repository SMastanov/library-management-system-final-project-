package library;

import java.util.List;

public class SearchResult<T extends LibraryItem> {
    private List<T> results;
    private String searchTerm;

    public SearchResult(List<T> results, String searchTerm) {
        this.results = results;
        this.searchTerm = searchTerm;
    }

    public void display() {
        if (results.isEmpty()) {
            System.out.println("  No results found for \"" + searchTerm + "\".");
            return;
        }
        System.out.println("  Found " + results.size() + " result(s) for \"" + searchTerm + "\":");
        results.forEach(LibraryItem::displayInfo);
    }

    public int getCount() { return results.size(); }

    public List<T> getResults() { return results; }
}
