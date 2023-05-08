package model;

import org.json.JSONObject;
import persistence.Writable;


// Represents a book having a title, author, and rating (1-5)

public class Book implements Writable {
    private String title;          // book title
    private String author;         // book author
    private int rating;            // book rating (1 - 5)

    // REQUIRES: rating must be one of 1, 2, 3, 4 or 5
    // EFFECTS: creates a book object with title, author, and rating
    public Book(String title, String author, int rating) {
        this.title = title;
        this.author = author;
        this.rating = rating;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }


    // Method taken from Thingy class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("rating", rating);
        return json;
    }

}

