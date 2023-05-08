package persistence;

import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Methods relies on JsonTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkBook(String title, String author, int rating, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(rating, book.getRating());
    }
}
