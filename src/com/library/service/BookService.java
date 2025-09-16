package com.library.service;

import com.library.dao.BookDao;
import com.library.dto.Book;
import com.library.repoistory.BookRepository;

public class BookService implements BookDao {
    private BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public BookService() {
    }

    @Override
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        bookRepo.addBook(book);
        System.out.println("Book added successfully: " + book);
    }

    @Override
    public Book updateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        return bookRepo.updateBook(book);
    }

    @Override
    public String removeBook(long bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        return bookRepo.removeBook(bookId);
    }

    @Override
    public Book getBookByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        return bookRepo.getBookByTitle(title);
    }

    @Override
    public Book getBookByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        return bookRepo.getBookByAuthor(author);
    }

    @Override
    public Book getBookByISBN(long isbn) {
        if (isbn <= 0) {
            throw new IllegalArgumentException("ISBN must be positive");
        }
        return bookRepo.getBookByISBN(isbn);
    }

    @Override
    public Book getBookById(long bookId) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive");
        }
        return bookRepo.getBookById(bookId);
    }

    @Override
    public boolean transferBook(long bookId, long fromBranchId, long toBranchId) {
        return bookRepo.transferBook(bookId, fromBranchId, toBranchId);
    }
}