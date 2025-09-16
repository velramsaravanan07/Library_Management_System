package com.library.service;
import com.library.dto.Book;
import com.library.dto.Patron;

public interface NotificationObserver {
    void onReservationAvailable(Patron patron, Book book);
    void onOverdueBook(Patron patron, Book book);
}