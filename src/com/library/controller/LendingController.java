package com.library.controller;

import com.library.dto.BorrowingRecord;
import com.library.helper.LibraryManagementFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LendingController {
    private static final Logger LOGGER = Logger.getLogger(LendingController.class.getName());
    private final LibraryManagementFacade facade;

    public LendingController(LibraryManagementFacade facade) {
        this.facade = facade;
        LOGGER.log(Level.INFO, "LendingController initialized");
    }

    public void borrowBook(long patronId, long bookId) {
        String result = facade.borrowBook(patronId, bookId);
        System.out.println(result);
    }

    public void returnBook(long patronId, long bookId) {
        String result = facade.returnBook(patronId, bookId);
        System.out.println(result);
    }

    public void reserveBook(long patronId, long bookId) {
        String result = facade.reserveBook(patronId, bookId);
        System.out.println(result);
    }

    public void viewOverdueBooks() {
        List<BorrowingRecord> overdues = facade.getOverdueBooks();
        if (overdues.isEmpty()) {
            System.out.println("No overdue books.");
        } else {
            System.out.println("Overdue Books:");
            for (BorrowingRecord record : overdues) {
                System.out.println("Record ID: " + record.getRecordId() + ", Book ID: " + record.getBookId() +
                        ", Patron ID: " + record.getPatronId() + ", Due: " + record.getDueDate());
            }
        }
    }
}