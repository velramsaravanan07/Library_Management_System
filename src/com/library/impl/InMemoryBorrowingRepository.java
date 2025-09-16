package com.library.impl;

import com.library.dto.*;

import com.library.repoistory.BorrowingRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryBorrowingRepository implements BorrowingRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryBorrowingRepository.class.getName());
    private final Map recordMap = new HashMap();
    private long nextRecordId = 1;

    @Override
    public void addRecord(BorrowingRecord record) {
        if (record == null) {
            LOGGER.log(Level.SEVERE, "Attempted to add null record");
            throw new IllegalArgumentException("Record cannot be null");
        }
        record.setRecordId(nextRecordId);
        recordMap.put(nextRecordId, record);
        LOGGER.log(Level.INFO, "Borrowing record added with ID: " + nextRecordId);
        nextRecordId++;
    }

    @Override
    public BorrowingRecord updateRecord(BorrowingRecord record) {
        if (record == null) {
            LOGGER.log(Level.SEVERE, "Attempted to update with null record");
            throw new IllegalArgumentException("Record to update cannot be null");
        }
        if (recordMap.containsKey(record.getRecordId())) {
            recordMap.put(record.getRecordId(), record);
            LOGGER.log(Level.INFO, "Borrowing record updated with ID: " + record.getRecordId());
            return record;
        }
        LOGGER.log(Level.WARNING, "Borrowing record not found for update with ID: " + record.getRecordId());
        return null;
    }

    @Override
    public BorrowingRecord findById(long recordId) {
        BorrowingRecord record = (BorrowingRecord) recordMap.get(recordId);
        if (record != null) {
            LOGGER.log(Level.INFO, "Retrieved borrowing record with ID: " + recordId);
        } else {
            LOGGER.log(Level.WARNING, "Borrowing record not found with ID: " + recordId);
        }
        return record;
    }

    @Override
    public List findByPatronId(long patronId) {
        List records = new ArrayList();
        for (Object recordObj : recordMap.values()) {
            BorrowingRecord record = (BorrowingRecord) recordObj;
            if (record.getPatronId() == patronId) {
                records.add(record);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + records.size() + " borrowing records for patron ID: " + patronId);
        return records;
    }

    @Override
    public List findByBookId(long bookId) {
        List records = new ArrayList();
        for (Object recordObj : recordMap.values()) {
            BorrowingRecord record = (BorrowingRecord) recordObj;
            if (record.getBookId() == bookId) {
                records.add(record);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + records.size() + " borrowing records for book ID: " + bookId);
        return records;
    }

    @Override
    public List findByStatus(BorrowingStatus status) {
        if (status == null) {
            LOGGER.log(Level.WARNING, "Attempted to find records with null status");
            return new ArrayList();
        }
        List records = new ArrayList();
        for (Object recordObj : recordMap.values()) {
            BorrowingRecord record = (BorrowingRecord) recordObj;
            if (record.getStatus() == status) {
                records.add(record);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + records.size() + " borrowing records with status: " + status);
        return records;
    }

    @Override
    public BorrowingRecord findActiveRecordByPatronAndBook(long patronId, long bookId) {
        for (Object recordObj : recordMap.values()) {
            BorrowingRecord record = (BorrowingRecord) recordObj;
            if (record.getPatronId() == patronId && record.getBookId() == bookId && record.getStatus() == BorrowingStatus.ACTIVE) {
                LOGGER.log(Level.INFO, "Found active borrowing record for patron ID: " + patronId + " and book ID: " + bookId);
                return record;
            }
        }
        LOGGER.log(Level.INFO, "No active borrowing record found for patron ID: " + patronId + " and book ID: " + bookId);
        return null;
    }

    @Override
    public int getActiveRecordCountByPatron(long patronId) {
        int count = 0;
        for (Object recordObj : recordMap.values()) {
            BorrowingRecord record = (BorrowingRecord) recordObj;
            if (record.getPatronId() == patronId && record.getStatus() == BorrowingStatus.ACTIVE) {
                count++;
            }
        }
        LOGGER.log(Level.INFO, "Active record count for patron ID: " + patronId + " is " + count);
        return count;
    }

    @Override
    public List findOverdueRecords() {
        List overdueRecords = new ArrayList();
        LocalDateTime now = LocalDateTime.now();
        for (Object recordObj : recordMap.values()) {
            BorrowingRecord record = (BorrowingRecord) recordObj;
            if (record.getStatus() == BorrowingStatus.ACTIVE && record.getDueDate() != null && now.isAfter(record.getDueDate())) {
                overdueRecords.add(record);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + overdueRecords.size() + " overdue borrowing records");
        return overdueRecords;
    }
}