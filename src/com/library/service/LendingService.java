package com.library.service;

import com.library.dto.*;
import com.library.repoistory.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LendingService {
    private static final Logger LOGGER = Logger.getLogger(LendingService.class.getName());
    private final BookRepository bookRepo;
    private final PatronRepository patronRepository;
    private final BorrowingRepository borrowingRecordRepo;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;
    private static final int MAX_BOOKS_PER_PATRON = 5;

    public LendingService(BookRepository bookRepo, PatronRepository patronRepository, BorrowingRepository borrowingRecordRepo, ReservationRepository reservationRepository, NotificationService notificationService) {
        this.bookRepo = bookRepo;
        this.patronRepository = patronRepository;
        this.borrowingRecordRepo = borrowingRecordRepo;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    public String borrowBook(long patronId, long bookId) {
        Patron patron = patronRepository.getPatronById(patronId);
        Book book = bookRepo.getBookById(bookId);

        if (patron == null) return "Patron not found";
        if (book == null) return "Book not found";
        if (!book.isAvailable()) return "Book not available";

        if (!canPatronBorrowMoreBooks(patronId)) {
            return "Patron has reached the maximum borrowing limit of " + MAX_BOOKS_PER_PATRON + " books";
        }

        BorrowingRecord existingRecord = borrowingRecordRepo.findActiveRecordByPatronAndBook(patronId, bookId);
        if (existingRecord != null) {
            return "Book is already borrowed by this patron";
        }

        BorrowingRecord record = new BorrowingRecord(0, patronId, bookId, book.getISBN(),
                LocalDateTime.now(), null,
                LocalDateTime.now().plusDays(14), BorrowingStatus.ACTIVE);
        borrowingRecordRepo.addRecord(record);
        book.setAvailable(false);
        bookRepo.updateBook(book);
        patron.addBorrowingRecord(record);
        patronRepository.updatePatron(patron);

        LOGGER.log(Level.INFO, "Book borrowed: patronId=" + patronId + ", bookId=" + bookId);
        return "Book borrowed successfully! Due date: " + record.getDueDate();
    }

    public String returnBook(long patronId, long bookId) {
        BorrowingRecord record = borrowingRecordRepo.findActiveRecordByPatronAndBook(patronId, bookId);
        if (record == null) return "No active borrowing record found";

        Book book = bookRepo.getBookById(bookId);
        if (book != null) {
            book.setAvailable(true);
            bookRepo.updateBook(book);
        }

        record.setReturnDate(LocalDateTime.now());
        record.setStatus(BorrowingStatus.RETURNED);
        borrowingRecordRepo.updateRecord(record);

        String message = "Book returned successfully!";
        if (LocalDateTime.now().isAfter(record.getDueDate())) {
            notificationService.notifyOverdueBook(patronId, bookId);
            message += " (Book was overdue)";
        }

        LOGGER.log(Level.INFO, "Book returned: patronId=" + patronId + ", bookId=" + bookId);
        return message;
    }

    public String getPatronBorrowingInfo(long patronId) {
        Patron patron = patronRepository.getPatronById(patronId);
        if (patron == null) return "Patron not found";

        int activeCount = borrowingRecordRepo.getActiveRecordCountByPatron(patronId);
        return String.format("Patron: %s, Active Borrowings: %d", patron.getName(), activeCount);
    }

    public String reserveBook(long patronId, long bookId) {
        Patron patron = patronRepository.getPatronById(patronId);
        Book book = bookRepo.getBookById(bookId);

        if (patron == null) return "Patron not found";
        if (book == null) return "Book not found";

        if (book.isAvailable()) {
            return "Book is currently available for borrowing";
        }

        List<Reservation> reservations = reservationRepository.findByPatronId(patronId);
        boolean hasActiveReservation = reservations.stream()
                .anyMatch(res -> res.getBookId() == bookId && res.getStatus() == ReservationStatus.ACTIVE);

        if (hasActiveReservation) {
            return "Patron already has an active reservation for this book";
        }

        Reservation reservation = new Reservation(0, patronId, bookId);
        reservationRepository.addReservation(reservation);

        LOGGER.log(Level.INFO, "Book reserved: patronId=" + patronId + ", bookId=" + bookId);
        return "Book reserved successfully! You will be notified when it becomes available.";
    }

    public List<BorrowingRecord> getPatronBorrowingHistory(long patronId) {
        return borrowingRecordRepo.findByPatronId(patronId);
    }

    public boolean canPatronBorrowMoreBooks(long patronId) {
        int activeCount = borrowingRecordRepo.getActiveRecordCountByPatron(patronId);
        return activeCount < MAX_BOOKS_PER_PATRON;
    }

    public List<BorrowingRecord> getOverdueBooks() {
        return borrowingRecordRepo.findOverdueRecords();
    }
}