package model;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


// represents a collection of books (ie. list of books)

public class BookList implements Writable {
    private List<Book> collection;
    private List<Book> sortedList;
    private List<Book> favouriteList;
    private String name;

    // EFFECTS: constructs an empty collection of books with a name
    public BookList(String name) {
        this.name = name;
        collection = new ArrayList<>();
    }


    // MODIFIES: this
    // EFFECTS: adds a book to BookList
    public void addBook(Book book) {
        EventLog.getInstance().logEvent(new Event("Added book: " + book.getTitle()));
        collection.add(book);
    }

    // MODIFIES: sortedList
    // EFFECTS: sorts the book collection from highest (5) to lowest (1)
    // if they have the same rating, whichever book was added first has higher priority
    public void sortBooksByRating(List<Book> collection) {
        sortedList = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            for (Book book : collection) {
                if (book.getRating() == i) {
                    sortedList.add(book);
                }
            }
        }
    }

    // MODIFIES: favouritesList
    // EFFECTS: adds books that the user rated as a 5 to a collection of favourites
    public void favouriteBookSorter(List<Book> collection) {
        EventLog.getInstance().logEvent(new Event("Viewed favourites list"));
        favouriteList = new ArrayList<>();
        for (Book book : collection) {
            if (book.getRating() == 5) {
                favouriteList.add(book);
            }
        }
    }


    // getters
    public List<Book> getCollection() {
        return collection;
    }

    public List<Book> getSortedList() {
        return sortedList;
    }

    public List<Book> getFavouriteList() {
        return favouriteList;
    }


    // EFFECTS: returns number of books in this booklist
    public int numBooks() {
        return collection.size();
    }

    public String getName() {
        return name;
    }

    // Method taken from WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("collection", booksToJson());
        return json;
    }

    // Method taken from WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: returns books in this booklist as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : collection) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}









