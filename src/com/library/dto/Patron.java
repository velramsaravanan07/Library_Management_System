package com.library.dto;

import com.library.service.NotificationObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patron implements NotificationObserver {

    private long patronId;
    private String name;
    private String email;
    private List<BorrowingRecord> borrowingHistory;
    private List<String> preferences;

    public Patron(long patronId, String name, String email) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.borrowingHistory = new ArrayList<>();
        this.preferences = new ArrayList<>();
    }

    public Patron(long patronId, String name, String email, List<BorrowingRecord> borrowingHistory) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.borrowingHistory = borrowingHistory != null ? borrowingHistory : new ArrayList<>();
        this.preferences = new ArrayList<>();
    }

    public Patron(long patronId, List<String> preferences, List<BorrowingRecord> borrowingHistory, String email, String name) {
        this.patronId = patronId;
        this.preferences = preferences != null ? preferences : new ArrayList<>();
        this.borrowingHistory = borrowingHistory != null ? borrowingHistory : new ArrayList<>();
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<BorrowingRecord> getBorrowingHistory() {
        return borrowingHistory;
    }

    public void setBorrowingHistory(List<BorrowingRecord> borrowingHistory) {
        this.borrowingHistory = borrowingHistory != null ? borrowingHistory : new ArrayList<>();
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences != null ? preferences : new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPatronId() {
        return patronId;
    }

    public void setPatronId(long patronId) {
        this.patronId = patronId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addBorrowingRecord(BorrowingRecord record) {
        if (this.borrowingHistory == null) {
            this.borrowingHistory = new ArrayList<>();
        }
        this.borrowingHistory.add(record);
    }

    public BorrowingRecord findActiveBorrowingRecord(long bookId) {
        if (this.borrowingHistory == null) {
            return null;
        }

        return this.borrowingHistory.stream()
                .filter(record -> record.getBookId() == bookId &&
                        record.getStatus() == BorrowingStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    public boolean setReturnDate(long bookId, LocalDateTime returnDate) {
        BorrowingRecord record = findActiveBorrowingRecord(bookId);
        if (record != null) {
            record.setReturnDate(returnDate);
            record.setStatus(BorrowingStatus.RETURNED);
            return true;
        }
        return false;
    }

    @Override
    public void onReservationAvailable(Patron patron, Book book) {
        System.out.println("NOTIFICATION: Dear " + name + ", your reserved book '" + book.getTitle() +
                "' is now available for pickup!");
    }

    @Override
    public void onOverdueBook(Patron patron, Book book) {
        System.out.println("OVERDUE NOTICE: Dear " + name + ", your book '" + book.getTitle() +
                "' is overdue. Please return it as soon as possible.");
    }
}