package ui;


import java.io.FileNotFoundException;

// creates a new instance of the book manager application and runs program
public class Main {
    public static void main(String[] args) {
        try {
            new GUI();
            new BookManagerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}







