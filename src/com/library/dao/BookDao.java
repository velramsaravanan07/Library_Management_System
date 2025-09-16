package com.library.dao;

import com.library.dto.Book;

public interface BookDao{

    public void addBook(Book book);

    public Book updateBook(Book book);

    public String removeBook(long bookId);

    public Book getBookByTitle(String title);

    public Book getBookByAuthor(String author);

    public Book getBookByISBN(long isbn);

    public Book getBookById(long bookId);

    public boolean transferBook(long bookId, long fromBranchId, long toBranchId);
}
