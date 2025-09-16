package com.library.impl;

import com.library.dto.BorrowingRecord;
import com.library.dto.BorrowingStatus;
import com.library.repoistory.BorrowingRecordRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryBorrowingRecordRespository implements BorrowingRecordRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryBorrowingRecordRespository.class.getName());
    private final Map<Long, BorrowingRecord> recordMap = new HashMap<>();
    private long nextRecordId = 1;

    @Override
    public void addRecord(BorrowingRecord record) {
        record.setRecordId(nextRecordId); // Fixed: Set record ID, not patron ID
        recordMap.put(nextRecordId, record);
        LOGGER.log(Level.INFO, "Borrowing record added with ID: " + nextRecordId);
        nextRecordId++;
    }

    @Override
    public BorrowingRecord getBorrowingRecordByRecordId(long recordId) {
        return recordMap.get(recordId);
    }

    @Override
    public BorrowingRecord updateBorrowingRecordByRecordId(long recordId, BorrowingRecord record) {
        if (record == null) {
            LOGGER.log(Level.SEVERE, "Attempted to update with null record");
            throw new IllegalArgumentException("Record to update cannot be null");
        }

        if (!recordMap.containsKey(recordId)) {
            LOGGER.log(Level.WARNING, "No borrowing record found with ID: " + recordId);
            throw new IllegalArgumentException("No borrowing record found with ID: " + recordId);
        }

        record.setRecordId(recordId);
        recordMap.put(recordId, record);
        LOGGER.log(Level.INFO, "Borrowing record updated with ID: " + recordId);
        return record;
    }

    @Override
    public BorrowingRecord findByPatronIdAndBookId(long patronId, long bookId) {
        return recordMap.values().stream()
                .filter(record -> record.getBookId() == bookId &&
                        record.getPatronId() == patronId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public BorrowingRecord findActiveBorrowingByPatronIdAndBookId(long patronId, long bookId) {
        return recordMap.values().stream()
                .filter(record -> record.getBookId() == bookId &&
                        record.getPatronId() == patronId &&
                        record.getStatus() == BorrowingStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    @Override
    public int getActiveBookCountForPatron(long patronId) {
        return (int) recordMap.values().stream()
                .filter(record -> record.getPatronId() == patronId &&
                        record.getStatus() == BorrowingStatus.ACTIVE)
                .count();
    }

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
            if (record.getStatus() == BorrowingStatus.ACTIVE && now.isAfter(record.getDueDate())) {
                overdueRecords.add(record);
            }
        }
        LOGGER.log(Level.INFO, "Retrieved " + overdueRecords.size() + " overdue borrowing records");
        return overdueRecords;
    }
}