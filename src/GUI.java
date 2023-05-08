package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Book;
import model.BookList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

// represents a Book Manager Application with a GUI
public class GUI extends JFrame implements ActionListener {

    private BookList bookList;
    private Book book;
    private static final String JSON_STORE = "data/booklist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    JList list = new JList();
    DefaultListModel<String> model = new DefaultListModel<>();

    // EFFECTS: initializes the Book Manager Application
    GUI() {
        bookList = new BookList("Dhara");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setFrame();
        setHeaderPanelAndTitle();
        setAddButton();
        setSaveButton();
        setLoadButton();
        setFavBooksButton();
        setJList();
    }

    // MODIFIES: this
    // EFFECTS: sets JFrame
    private void setFrame() {
        this.setVisible(true);
        this.setSize(300, 500);
        this.setTitle("Book Manager");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(215, 189, 226));
        this.setLayout(null);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println((event));
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a JList on a JPanel with scroll functionality
    private void setJList() {

        list.setModel(model);
        listSelectionListener();
        list.setVisibleRowCount(13);
        list.setFixedCellWidth(200);
        list.setFont(new Font("Desdemona", Font.PLAIN, 15));
        JPanel panel = new JPanel();
        panel.setBounds(47, 170, 200, 250);
        panel.add(new JScrollPane(list));
        panel.setOpaque(false);
        add(panel);
        revalidate();
    }

    // MODIFIES: this, list
    // EFFECTS: sets up SelectionListener to select a book on the JList and show book details
    private void listSelectionListener() {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            for (Book book : bookList.getCollection()) {
                String selected = list.getSelectedValue().toString();
                if (book.getTitle().equals(selected)) {
                    JOptionPane.showMessageDialog(null, "Title: " + book.getTitle()
                            + "\nAuthor: " + book.getAuthor() + "\nRating: " + book.getRating());
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates an "Add" book button with action listener
    private void setAddButton() {
        ImageIcon bookIcon = new ImageIcon("src/main/ui/book.png");
        JButton add = new JButton();
        add.setBounds(70, 85, 150, 60);
        add.addActionListener(this);
        add.setText("Add");
        add.setFont(new Font("Desdemona", Font.PLAIN, 20));
        add.setIcon(bookIcon);
        this.add(add);
    }

    // MODIFIES: this
    // EFFECTS: creates a "Save" button with action listener
    private void setSaveButton() {
        JButton save = new JButton();
        save.setBounds(45, 410, 100, 40);
        save.addActionListener(this);
        save.setText("Save");
        save.setFont(new Font("Desdemona", Font.PLAIN, 20));
        this.add(save);
    }

    // MODIFIES: this
    // EFFECTS: creates a "Load" button with action listener
    private void setLoadButton() {
        JButton load = new JButton();
        load.setBounds(150, 410, 100, 40);
        load.addActionListener(this);
        load.setText("Load");
        load.setFont(new Font("Desdemona", Font.PLAIN, 20));
        this.add(load);
    }

    // MODIFIES: this
    // EFFECTS: creates a "Your Favourite Books" button with action listener
    private void setFavBooksButton() {
        JButton fav = new JButton();
        fav.setBounds(85, 150, 120, 20);
        fav.addActionListener(this);
        fav.setText("Your favorite books");
        fav.setFont(new Font("Desdemona", Font.PLAIN, 10));
        this.add(fav);
    }


    // MODIFIES: this
    // EFFECTS: creates a header panel with title
    private void setHeaderPanelAndTitle() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(99, 57, 116));
        topPanel.setBounds(0, 0, 300, 70);
        topPanel.setLayout(new BorderLayout());
        this.add(topPanel);
        JLabel title = new JLabel();
        title.setText("Book Manager");
        title.setFont(new Font("Desdemona", Font.PLAIN, 40));
        title.setForeground(Color.WHITE);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(title);
    }

    // MODIFIES: this, bookList, list
    // EFFECTS: performs EventHandling for different button clicks
    // If "Add" button is pressed, adds a new book to BookList and adds book to list
    // if "Save" button is pressed, saves bookList
    // if "Load" button is pressed, loads bookList
    // if "Your Favourite Books" button is pressed, prints a list of favourite books
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add")) {
            String inputTitle = JOptionPane.showInputDialog("Enter title:");
            String inputAuthor = JOptionPane.showInputDialog("Enter author:");
            String inputRating = JOptionPane.showInputDialog("Enter rating:");
            int convertedRating = Integer.parseInt(inputRating);
            book = new Book(inputTitle, inputAuthor, convertedRating);
            bookList.addBook(book);
            model.addElement(book.getTitle());
        } else if (e.getActionCommand().equals("Save")) {
            saveBooks();
            JOptionPane.showMessageDialog(null, "Your book collection has been saved.");
        } else if (e.getActionCommand().equals("Load")) {
            loadBooks();
        } else if (e.getActionCommand().equals("Your favorite books")) {
            printFavoriteBooks();
        }
    }

    // EFFECTS: saves Booklist to file
    public void saveBooks() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, BookList, list
    // EFFECTS: populates Booklist from file onto list
    public void loadBooks() {
        try {
            bookList = jsonReader.read();
            for (Book book : bookList.getCollection()) {
                model.addElement(book.getTitle());
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, favouritesList
    // EFFECTS: prints a list of favourite books
    public void printFavoriteBooks() {
        String favBooksList = "";
        bookList.favouriteBookSorter(bookList.getCollection());
        for (Book book : bookList.getFavouriteList()) {
            favBooksList += book.getTitle() + "\n";
        }
        JOptionPane.showMessageDialog(null, favBooksList);
    }
}

