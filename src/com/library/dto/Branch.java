package com.library.dto;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Branch {
    private static final Logger LOGGER = Logger.getLogger(Branch.class.getName());
    private long branchId;
    private String name;
    private String address;
    private List<Long> bookIds;
    public Branch(long id, String name, String address) {
        this.branchId = id;
        this.name = name;
        this.address = address;
    }

    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public Branch(long id, List<Long> bookIds, String address, String name) {
        this.branchId = id;
        this.bookIds = bookIds;
        this.address = address;
        this.name = name;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    public long getId() {
        return branchId;
    }

    public void setId(long id) {
        this.branchId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + branchId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public boolean addBook(long bookId) {
        if (bookId <= 0) {
            LOGGER.log(Level.WARNING, "Invalid book ID for adding to branch: " + bookId);
            return false;
        }
        if (!bookIds.contains(bookId)) {  // Avoid duplicates
            bookIds.add(bookId);
            LOGGER.log(Level.INFO, "Book ID " + bookId + " added to branch " + branchId);
            return true;
        }
        LOGGER.log(Level.INFO, "Book ID " + bookId + " already exists in branch " + branchId);
        return false;  // Already present
    }

    public boolean removeBook(long bookId) {
        if (bookId <= 0) {
            LOGGER.log(Level.WARNING, "Invalid book ID for removing from branch: " + bookId);
            return false;
        }
        boolean removed = bookIds.remove(Long.valueOf(bookId));  // Use Long for autoboxing
        if (removed) {
            LOGGER.log(Level.INFO, "Book ID " + bookId + " removed from branch " + branchId);
        } else {
            LOGGER.log(Level.WARNING, "Book ID " + bookId + " not found in branch " + branchId);
        }
        return removed;
    }
}