package main.java.com.siddharth.librarysystem.commandline;
import java.util.List;
import java.util.Scanner;

import main.java.com.siddharth.librarysystem.core.Library;
import main.java.com.siddharth.librarysystem.entity.Book;
import main.java.com.siddharth.librarysystem.entity.Patron;

/**
 * Handles all command-line interface interactions for the Library System.
 */
public class LibraryCLI {
    private final Library library;
    private final Scanner scanner;

    public LibraryCLI(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the main loop of the command-line interface.
     */
    public void start() {
        System.out.println("Welcome to the Library Management System!");
        int choice;
        do {
            printMenu();
            choice = getIntInput();
            handleMenuChoice(choice);
        } while (choice != 0);
        System.out.println("Thank you for using the Library Management System. Goodbye!");
    }

    private void printMenu() {
        System.out.println("\n--- Library Menu ---");
        System.out.println("1. Add a new book to the inventory");
        System.out.println("2. Register a new patron");
        System.out.println("3. Issue a book to a patron");
        System.out.println("4. Return a book from a patron");
        System.out.println("5. Search for a book by title");
        System.out.println("6. List all books");
        System.out.println("7. List books borrowed by a patron");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1: handleAddBook(); break;
            case 2: handleRegisterPatron(); break;
            case 3: handleIssueBook(); break;
            case 4: handleReturnBook(); break;
            case 5: handleSearchByTitle(); break;
            case 6: handleListAllBooks(); break;
            case 7: handleListBorrowedBooks(); break;
            case 0: break;
            default: System.out.println("Invalid choice. Please try again.");
        }
    }

    private void handleAddBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter publication year: ");
        int year = getIntInput();
        System.out.print("Enter quantity to add: ");
        int quantity = getIntInput();

        Book newBook = new Book(title, author, isbn, year);
        library.addNewBook(newBook, quantity);
    }

    private void handleRegisterPatron() {
        System.out.println("\n--- Register New Patron ---");
        System.out.print("Enter patron name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patron email: ");
        String email = scanner.nextLine();

        Patron newPatron = library.registerNewPatron(name, email);
        System.out.println("Patron registered with ID: " + newPatron.getId());
    }

    private void handleIssueBook() {
        System.out.println("\n--- Issue Book ---");
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        Patron patron = library.findPatronById(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        System.out.print("Enter Book ISBN to issue: ");
        String isbn = scanner.nextLine();
        Book book = library.searchBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        
        library.issueBookToPatron(book, patron);
    }
    
    private void handleReturnBook() {
        System.out.println("\n--- Return Book ---");
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        Patron patron = library.findPatronById(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        System.out.print("Enter Book ISBN to return: ");
        String isbn = scanner.nextLine();
        Book book = library.searchBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        
        library.returnBookFromPatron(book, patron);
    }
    
    private void handleSearchByTitle() {
        System.out.println("\n--- Search for Book by Title ---");
        System.out.print("Enter the title to search for: ");
        String title = scanner.nextLine();
        List<Book> books = library.searchBookByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No books found with that title.");
        } else {
            System.out.println("Found Books:");
            books.forEach(book -> System.out.println(" - " + book + ", Available Copies: " + library.getAvailableCopies(book)));
        }
    }
    
    private void handleListAllBooks() {
        System.out.println("\n--- All Books in Inventory ---");
        List<Book> allBooks = library.listAllBooks();
        if (allBooks.isEmpty()) {
            System.out.println("The library has no books.");
        } else {
            allBooks.forEach(book -> System.out.println(" - " + book + ", Available Copies: " + library.getAvailableCopies(book)));
        }
    }
    
    private void handleListBorrowedBooks() {
        System.out.println("\n--- List Borrowed Books by Patron ---");
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        Patron patron = library.findPatronById(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        List<Book> borrowedBooks = library.getBooksBorrowedByPatron(patron);
        if (borrowedBooks.isEmpty()) {
            System.out.println(patron.getName() + " has not borrowed any books.");
        } else {
            System.out.println("Books borrowed by " + patron.getName() + ":");
            borrowedBooks.forEach(book -> System.out.println(" - " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")"));
        }
    }

    /**
     * Helper method to safely read an integer from the console.
     */
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}