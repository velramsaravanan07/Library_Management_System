package com.library.repoistory;

import com.library.dto.BorrowingRecord;
import com.library.dto.BorrowingStatus;

import java.util.List;
import java.util.Optional;

public interface BorrowingRepository {
    void addRecord(BorrowingRecord record);
    BorrowingRecord updateRecord(BorrowingRecord record);
    BorrowingRecord findById(long recordId);
    List findByPatronId(long patronId);
    List findByBookId(long bookId);
    List findByStatus(BorrowingStatus status);
    BorrowingRecord findActiveRecordByPatronAndBook(long patronId, long bookId);
    int getActiveRecordCountByPatron(long patronId);
    List findOverdueRecords();
}