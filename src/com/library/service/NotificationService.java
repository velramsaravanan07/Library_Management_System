package com.library.service;

import com.library.dto.Book;
import com.library.dto.Patron;
import com.library.repoistory.BookRepository;
import com.library.repoistory.PatronRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationService {
    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;
    private final List<NotificationObserver> observers;

    public NotificationService(PatronRepository patronRepository, BookRepository bookRepository) {
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
        this.observers = new ArrayList<>();
    }

    public void registerObserver(NotificationObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
            LOGGER.log(Level.INFO, "Registered observer for notifications");
        }
    }

    public void unregisterObserver(NotificationObserver observer) {
        observers.remove(observer);
        LOGGER.log(Level.INFO, "Unregistered observer from notifications");
    }

    public void notifyReservationAvailable(long patronId, long bookId) {
        Patron patron = patronRepository.getPatronById(patronId);
        Book book = bookRepository.getBookById(bookId);

        if (patron != null && book != null) {
            for (NotificationObserver observer : observers) {
                observer.onReservationAvailable(patron, book);
            }
            LOGGER.log(Level.INFO, "Notified observers for reservation available: patronId=" + patronId + ", bookId=" + bookId);
        } else {
            LOGGER.log(Level.WARNING, "Failed to notify: Patron or book not found for patronId=" + patronId + ", bookId=" + bookId);
        }
    }

    public void notifyOverdueBook(long patronId, long bookId) {
        Patron patron = patronRepository.getPatronById(patronId);
        Book book = bookRepository.getBookById(bookId);

        if (patron != null && book != null) {
            for (NotificationObserver observer : observers) {
                observer.onOverdueBook(patron, book);
            }
            LOGGER.log(Level.INFO, "Notified observers for overdue book: patronId=" + patronId + ", bookId=" + bookId);
        } else {
            LOGGER.log(Level.WARNING, "Failed to notify: Patron or book not found for patronId=" + patronId + ", bookId=" + bookId);
        }
    }
}