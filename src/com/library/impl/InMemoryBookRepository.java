package com.library.impl;

import com.library.dto.*;
import com.library.repoistory.BookRepository;
import com.library.repoistory.BranchRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryBookRepository implements BookRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryBookRepository.class.getName());
    private final Map<Long, Book> bookMap = new HashMap<>();
    private long nextId = 1;
    private final BranchRepository branchRepository;

    @Override
    public void addBook(Book book) {
        book.setBookid(nextId);
        bookMap.put(nextId, book);
        LOGGER.log(Level.INFO, "Book added with ID: " + nextId);
        nextId++;
    }

    public InMemoryBookRepository(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }



    @Override
    public Book updateBook(Book book) {
        if (bookMap.containsKey(book.getBookid())) {
            bookMap.put(book.getBookid(), book);
            LOGGER.log(Level.INFO, "Book updated with ID: " + book.getBookid());
            return book;
        }
        LOGGER.log(Level.WARNING, "Book not found for update with ID: " + book.getBookid());
        return null;
    }

    @Override
    public String removeBook(long bookId) {
        if (bookMap.containsKey(bookId)) {
            Book removedBook = bookMap.remove(bookId);
            LOGGER.log(Level.INFO, "Book removed with ID: " + bookId);
            return "Book removed successfully: " + removedBook.getTitle();
        }
        LOGGER.log(Level.WARNING, "Book not found for removal with ID: " + bookId);
        return "Book not found with ID: " + bookId;
    }

    @Override
    public Book getBookByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid title provided for search");
            return null;
        }

        return bookMap.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title.trim()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Book getBookByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid author provided for search");
            return null;
        }

        return bookMap.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author.trim()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Book getBookByISBN(long isbn) {
        String isbnString = String.valueOf(isbn);
        return bookMap.values().stream()
                .filter(book -> isbnString.equals(book.getISBN()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Book getBookById(long bookId) {
        return bookMap.get(bookId);
    }

    @Override
    public List findAvailableBooks() {
        List availableBooks = new ArrayList();
        for (Object bookObj : bookMap.values()) {
            Book book = (Book) bookObj;
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + availableBooks.size() + " available books");
        return availableBooks;
    }

    public List findByBranch(long branchId) {
        List branchBooks = new ArrayList();
        for (Object bookObj : bookMap.values()) {
            Book book = (Book) bookObj;
            if (book.getBranchId() == branchId) {
                branchBooks.add(book);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + branchBooks.size() + " books for branch ID: " + branchId);
        return branchBooks;
    }
    @Override
    public boolean transferBook(long bookId, long fromBranchId, long toBranchId) {
        if (bookId <= 0 || fromBranchId <= 0 || toBranchId <= 0) {
            LOGGER.log(Level.WARNING, "Invalid transfer parameters: bookId=" + bookId +
                    ", fromBranchId=" + fromBranchId + ", toBranchId=" + toBranchId);
            return false;
        }
        Book book = bookMap.get(bookId);
        if (book == null || book.getBranchId() != fromBranchId) {
            LOGGER.log(Level.WARNING, "Book not found or not in specified branch: bookId=" +
                    bookId + ", fromBranchId=" + fromBranchId);
            return false;
        }
        if (branchRepository == null) {
            LOGGER.log(Level.SEVERE, "BranchRepository is null, cannot transfer book");
            return false;
        }
        Branch fromBranch = branchRepository.findById(fromBranchId);
        Branch toBranch = branchRepository.findById(toBranchId);
        if (fromBranch == null || toBranch == null) {
            LOGGER.log(Level.WARNING, "One or both branches not found: fromBranchId=" +
                    fromBranchId + ", toBranchId=" + toBranchId);
            return false;
        }
        book.setBranchId(toBranchId);
        bookMap.put(bookId, book);
        fromBranch.removeBook(bookId);
        toBranch.addBook(bookId);
        branchRepository.updateBranch(fromBranch);
        branchRepository.updateBranch(toBranch);
        LOGGER.log(Level.INFO, "Book transferred: bookId=" + bookId +
                " from branchId=" + fromBranchId + " to branchId=" + toBranchId);
        return true;
    }
}


