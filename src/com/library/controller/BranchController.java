package com.library.controller;

import com.library.dto.Book;
import com.library.dto.Branch;
import com.library.helper.LibraryManagementFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BranchController {
    private static final Logger LOGGER = Logger.getLogger(BranchController.class.getName());
    private final LibraryManagementFacade facade;

    public BranchController(LibraryManagementFacade facade) {
        this.facade = facade;
        LOGGER.log(Level.INFO, "BranchController initialized");
    }

    public void addBranch(String name, String address) {
        try {
            Branch branch = new Branch(name, address);
            facade.addBranch(branch);
            System.out.println("Branch added: " + name);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Failed to add branch: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void transferBook(long bookId, long fromBranchId, long toBranchId) {
        try {
            boolean success = facade.transferBook(bookId, fromBranchId, toBranchId);
            if (success) {
                System.out.println("Book ID " + bookId + " transferred from branch " + fromBranchId + " to " + toBranchId);
            } else {
                System.out.println("Failed to transfer book ID " + bookId);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Failed to transfer book: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }
}