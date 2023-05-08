package test;

import model.BookList;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookListTest {
    private BookList testBookList;
    private Book testBook;
    private Book testBook2;
    private Book testBook3;
    private Book testBook4;

    @BeforeEach
    public void setUp() {
        testBookList = new BookList("Dhara");
        testBook = new Book("Recursion", "Blake Crouch", 4);
        testBook2 = new Book("Jade Legacy", "Fonda Lee", 5);
        testBook3 = new Book("A Really Bad Book", "Bad Writer", 1);
        testBook4 = new Book("Jade City", "Fonda Lee", 5);
    }

    @Test
    public void testConstructor() {
        assertEquals(testBookList.getCollection().size(), 0);
    }

    @Test
    public void testAddBook() {
        testBookList.addBook(testBook);
        assertEquals(testBookList.getCollection().size(), 1);
        testBookList.addBook(testBook2);
        assertEquals(testBookList.getCollection().size(), 2);
    }

    @Test
    public void testSortBooks() {
        testBookList.addBook(testBook);
        testBookList.addBook(testBook2);
        testBookList.addBook(testBook3);
        testBookList.addBook(testBook4);
        testBookList.sortBooksByRating(testBookList.getCollection());
        assertEquals(3, testBookList.getSortedList().indexOf(testBook3));
        assertEquals(2, testBookList.getSortedList().indexOf(testBook));
        assertEquals(0, testBookList.getSortedList().indexOf(testBook2));
        assertEquals(1, testBookList.getSortedList().indexOf(testBook4));

    }

    @Test

    public void testFavouritesSorter() {
        testBookList.addBook(testBook);
        testBookList.addBook(testBook2);
        testBookList.addBook(testBook3);
        testBookList.addBook(testBook4);
        testBookList.favouriteBookSorter(testBookList.getCollection());
        assertEquals(testBookList.getFavouriteList().size(), 2);
    }

}
