package ui;

import java.util.Scanner;
import java.lang.String;

import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.BookList;
import model.Book;



// represents the BookManager application
public class BookManagerApp {

    private static final String JSON_STORE = "data/booklist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String VIEW_COMMAND = "view";
    private static final String ADD_COMMAND = "add";
    private static final String SORT_COMMAND = "sort";
    private static final String QUIT_COMMAND = "quit";
    private static final String SELECT_COMMAND = "select";
    private static final String LOAD_COMMAND = "load";

    private final Scanner input;
    private BookList bookList;
    private Book book;


    // EFFECTS: creates a new BookList, and prints out a welcome message with options
    public BookManagerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        bookList = new BookList("Dhara");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        System.out.println("Hi! Welcome to your Book Manager.");
        System.out.println();
        printInstructions();
    }

    // MODIFIES: input
    // EFFECTS: prints all user options and gets user choice
    // reference: FitGymKiosk
    private void printInstructions() {
        System.out.println("Enter '" + LOAD_COMMAND + "' to load your saved book collection");
        System.out.println("Enter '" + VIEW_COMMAND + "' to view your book collection");
        System.out.println("Enter '" + SELECT_COMMAND + "' to choose a book to explore");
        System.out.println("Enter '" + ADD_COMMAND + "' to add a book");
        System.out.println("Enter '" + SORT_COMMAND + "' to sort books by rating");
        System.out.println("Enter '" + QUIT_COMMAND + "' to exit program");

        String userInput = input.nextLine();
        followInput(userInput);
    }


    // EFFECTS: follows user input into its specific function/handling
    // reference: FitGymKiosk
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void followInput(String userInput) {
        if (userInput.length() > 0) {
            switch (userInput) {
                case VIEW_COMMAND:
                    viewBooks();
                    break;
                case ADD_COMMAND:
                    addBook();
                    break;
                case SORT_COMMAND:
                    viewSortedBooks();
                    break;
                case QUIT_COMMAND:
                    quit();
                    break;
                case SELECT_COMMAND:
                    select();
                    break;
                case LOAD_COMMAND:
                    loadBookList();
                    break;
                default:
                    System.out.println("Sorry, that is not a valid command. Please try again." + "\n");
                    printInstructions();
                    break;
            }
        }
    }


    // EFFECTS: prints a list of the books in user's book collection (most recently added goes last)
    private void viewBooks() {
        System.out.println("Your Books:");
        for (Book book : bookList.getCollection()) {
            System.out.println(book.getTitle());
        }
        System.out.println();
        printInstructions();
    }

    // REQUIRES: Rating entered by user must be 1, 2, 3, 4 or 5
    // MODIFIES: BookList (collection)
    // EFFECTS: gets user input for title, author and rating and creates a new book object
    private void addBook() {
        System.out.println("Please enter the title of the book: ");
        String titleInput = input.nextLine();
        System.out.println("Please enter the author of the book: ");
        String authorInput = input.nextLine();
        System.out.println("Please enter the rating of the book: ");
        int ratingInput = Integer.parseInt(input.nextLine());
        book = new Book(titleInput, authorInput, ratingInput);
        bookList.addBook(book);
        System.out.println("Your book has been successfully added");
        System.out.println();
        printInstructions();
    }


    // MODIFIES: BookList (sortedList)
    // EFFECTS: prints out list of books sorted by rating (highest to lowest)
    private void viewSortedBooks() {
        bookList.sortBooksByRating(bookList.getCollection());
        System.out.println("Your Sorted Books:");
        for (Book book : bookList.getSortedList()) {
            System.out.println(book.getTitle() + " (" + book.getRating() + ")");
        }
        System.out.println();
        printInstructions();
    }

    // try, catch block taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // other references: FitGymKiosk

    // EFFECTS: asks user whether they want to save their book collection
    // if yes, work is saved and user gets confirmation message, then program ends
    // if no, program quits immediately
    private void quit() {
        System.out.println("Did you want to save your work before quitting?");
        System.out.println("Select '1' to save and quit, '0' to quit without saving");
        String saveWork = input.nextLine();
        if (saveWork.equals("1")) {
            try {
                jsonWriter.open();
                jsonWriter.write(bookList);
                jsonWriter.close();
                System.out.println("Saved " + bookList.getName() + "'s BookList to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        System.out.println("Quitting...");
        input.close();
        for (Event event: EventLog.getInstance()) {
            System.out.println((event));
        }
    }

    // REQUIRES: book title must be part of book collection
    // EFFECTS: gets user to choose a book from list, and prints book details (title, author, rating)
    private void select() {
        for (Book book : bookList.getCollection()) {
            System.out.println(book.getTitle());
        }
        System.out.println();
        System.out.println("Please enter a book from your list: ");
        String bookInput = input.nextLine();
        Book selectedBook = findBook(bookInput);
        printBookInfo(selectedBook);
        System.out.println();
        printInstructions();
    }

    // REQUIRES: book title must correspond to a book object in collection
    // EFFECTS: finds corresponding book object given book title
    private Book findBook(String title) {
        for (Book book : bookList.getCollection()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // EFFECTS: prints book details (title, author, rating)
    private void printBookInfo(Book book) {
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Rating: " + book.getRating());
    }

    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: loads booklist from file
    private void loadBookList() {
        try {
            bookList = jsonReader.read();
            System.out.println("Loaded " + bookList.getName() + "'s List from " + JSON_STORE);
            System.out.println();
            printInstructions();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}


