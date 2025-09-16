package com.library.repoistory;

import com.library.dto.BorrowingRecord;

import java.util.List;

public interface BorrowingRecordRepository {

    public void addRecord(BorrowingRecord record);

    public BorrowingRecord getBorrowingRecordByRecordId(long recordId);

    public BorrowingRecord updateBorrowingRecordByRecordId(long recordId, BorrowingRecord record);

    BorrowingRecord findByPatronIdAndBookId(long patronId, long bookId);

    public BorrowingRecord findActiveBorrowingByPatronIdAndBookId(long patronId, long bookId) ;


    int getActiveBookCountForPatron(long patronId);


    List findByPatronId(long patronId);

    int getActiveRecordCountByPatron(long patronId);

    List findOverdueRecords();
}
