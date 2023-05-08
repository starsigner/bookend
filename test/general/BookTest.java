package test;

import org.junit.jupiter.api.BeforeEach;
import model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book1;

    @BeforeEach
    public void setUp() {
        book1 = new Book("Recursion", "Blake Crouch", 4);
    }

    @Test
    public void testConstructor() {
        assertEquals("Recursion", book1.getTitle());
        assertEquals("Blake Crouch", book1.getAuthor());
        assertEquals(4, book1.getRating());
    }

}

