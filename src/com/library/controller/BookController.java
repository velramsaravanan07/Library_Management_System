package com.library.controller;

import com.library.dto.Book;
import com.library.helper.LibraryManagementFacade;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookController {
    private static final Logger LOGGER = Logger.getLogger(BookController.class.getName());
    private final LibraryManagementFacade facade;

    public BookController(LibraryManagementFacade facade) {
        this.facade = facade;
        LOGGER.log(Level.INFO, "BookController initialized");
    }

    public void addBook(String title, String author, String isbn, int publicationYear, long branchId) {
        try {
            Book book = new Book(0, title, author, isbn, publicationYear, true, branchId);
            facade.addBook(book);
            System.out.println("Book added: " + title);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Failed to add book: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchBook(int searchType, String term) {
        Book book = null;
        switch (searchType) {
            case 1:
                book = facade.findBookByTitle(term);
                break;
            case 2:
                book = facade.findBookByAuthor(term);
                break;
                case 3:
                long isbn = Long.parseLong(term.replaceAll("[^0-9]", ""));  // Parse as long
                book = facade.findBookByISBN(isbn);
                break;
            default:
                System.out.println("Invalid search type.");
                return;
        }
        if (book != null) {
            System.out.println("Found: " + book.getTitle() + " by " + book.getAuthor() + ", ISBN: " + book.getISBN());
        } else {
            System.out.println("Book not found for: " + term);
        }
    }
}