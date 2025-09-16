package com.library.repoistory;

import com.library.dto.Book;

import java.util.List;

public interface BookRepository {

    public void addBook(Book book);

    public Book updateBook(Book book);

    public String removeBook(long bookId);

    public Book getBookByTitle(String title);

    public Book getBookByAuthor(String author);

    public Book getBookByISBN(long isbn);

    public Book getBookById(long bookId);


    List findAvailableBooks();

    List findByBranch(long branchId);
    boolean transferBook(long bookId, long fromBranchId, long toBranchId);
}
