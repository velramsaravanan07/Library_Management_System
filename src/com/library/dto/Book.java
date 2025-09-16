package com.library.dto;

public class Book {

    private long bookid;
    private String title;
    private String author;
    private String ISBN;
    private int publicationYear;
    private boolean isAvailable = true;
    private long branchId;

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getAuthor() {
        return author;
    }

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Book(long id, String title, String author, String ISBN, int publicationYear) {
        this.bookid = id;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
    }

    public Book(long bookid, String title, String author, String ISBN, int publicationYear, boolean isAvailable) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.isAvailable = isAvailable;
    }

    public void setbookId(long l) {
    }

    public Book(long bookid, String title, String author, String ISBN, int publicationYear, boolean isAvailable, long branchId) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.isAvailable = isAvailable;
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", publicationYear=" + publicationYear +
                ", isAvailable=" + isAvailable +
                ", branchId=" + branchId +
                '}';
    }


}