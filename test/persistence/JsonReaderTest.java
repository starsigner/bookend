package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.util.List;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// All Test methods rely on JSONReaderTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookList bl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBookList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookList.json");
        try {
            BookList bl = reader.read();
            assertEquals("My book list", bl.getName());
            assertEquals(0, bl.numBooks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBookList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookList.json");
        try {
            BookList bl = reader.read();
            assertEquals("My book list", bl.getName());
            List<Book> collection = bl.getCollection();
            assertEquals(2, bl.numBooks());
            checkBook("Recursion", "Blake Crouch", 4, collection.get(0));
            checkBook("Jade Legacy", "Fonda Lee", 5, collection.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}