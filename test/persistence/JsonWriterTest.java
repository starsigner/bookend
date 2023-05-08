package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// All Test methods rely on JsonWriterTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            BookList bl = new BookList("My book list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBookList() {
        try {
            BookList bl = new BookList("My book list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookList.json");
            bl = reader.read();
            assertEquals("My book list", bl.getName());
            assertEquals(0, bl.numBooks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBookList() {
        try {
            BookList bl = new BookList("My book list");
            bl.addBook(new Book("Recursion", "Blake Crouch", 4));
            bl.addBook(new Book("Jade Legacy", "Fonda Lee", 5));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookList.json");
            writer.open();
            writer.write(bl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBookList.json");
            bl = reader.read();
            assertEquals("My book list", bl.getName());
            List<Book> collection = bl.getCollection();
            assertEquals(2, collection.size());
            checkBook("Recursion", "Blake Crouch", 4, collection.get(0));
            checkBook("Jade Legacy", "Fonda Lee", 5, collection.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}