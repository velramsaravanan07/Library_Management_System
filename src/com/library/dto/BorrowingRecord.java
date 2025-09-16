package com.library.dto;

import java.time.LocalDateTime;

public class BorrowingRecord {

    private long recordId;
    private long patronId;
    private long bookId;
    private String isbn;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    private BorrowingStatus status;



    public BorrowingRecord(long recordId, long patronId, long bookId, String isbn, LocalDateTime borrowDate, LocalDateTime returnDate, LocalDateTime dueDate, BorrowingStatus status) {
        this.recordId = recordId;
        this.patronId = patronId;
        this.bookId = bookId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public BorrowingRecord(long l, long patronId, String isbn, LocalDateTime now, LocalDateTime returnDate) {
    }

    public BorrowingRecord() {
    }


    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getPatronId() {
        return patronId;
    }

    public void setPatronId(long patronId) {
        this.patronId = patronId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public BorrowingStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowingStatus status) {
        this.status = status;
    }
}
