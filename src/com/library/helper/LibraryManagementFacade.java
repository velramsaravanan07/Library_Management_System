package com.library.helper;

import com.library.dto.*;
import com.library.impl.*;
import com.library.repoistory.*;
import com.library.service.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryManagementFacade {
    private static final Logger LOGGER = Logger.getLogger(LibraryManagementFacade.class.getName());
    private final BookService bookService;
    private final PatronService patronService;
    private final LendingService lendingService;
    private final BranchService branchService;
    private final RecommendationService recommendationService;
    private final NotificationService notificationService;

    public LibraryManagementFacade() {
        BranchRepository branchRepo = new InMemoryBranchRepository();
        BookRepository bookRepo = new InMemoryBookRepository(branchRepo);
        PatronRepository patronRepo = new InMemoryPatronRepository();
        BorrowingRepository borrowingRepo = new InMemoryBorrowingRepository();
        ReservationRepository reservationRepo = new InMemoryReservationRepository();

        this.bookService = new BookService(bookRepo);
        this.patronService = new PatronService(patronRepo);
        this.notificationService = new NotificationService(patronRepo, bookRepo);
        this.lendingService = new LendingService(bookRepo, patronRepo, borrowingRepo, reservationRepo, notificationService);
        this.branchService = new BranchService(branchRepo, bookRepo);
        this.recommendationService = new RecommendationService(bookRepo, patronRepo, borrowingRepo);

        List<Patron> allPatrons = patronRepo.findAllPatrons();
        for (Patron patron : allPatrons) {
            notificationService.registerObserver(patron);
        }
        LOGGER.log(Level.INFO, "Facade initialized; registered " + allPatrons.size() + " patrons as observers");
    }

    // Book Management
    public void addBook(Book book) {
        bookService.addBook(book);
    }

    public Book updateBook(Book book) {
        return bookService.updateBook(book);
    }

    public String removeBook(long bookId) {
        return bookService.removeBook(bookId);
    }

    public Book findBookByTitle(String title) {
        return bookService.getBookByTitle(title);
    }

    public Book findBookByAuthor(String author) {
        return bookService.getBookByAuthor(author);
    }

    public Book findBookByISBN(long isbn) {  // Changed to long
        return bookService.getBookByISBN(isbn);
    }

    public Book findBookById(long bookId) {
        return bookService.getBookById(bookId);
    }

    public boolean transferBook(long bookId, long fromBranchId, long toBranchId) {
        return bookService.transferBook(bookId, fromBranchId, toBranchId);
    }

    // ... (Other methods unchanged: Patron, Lending, Branch, Recommendation, Notification)

    // Service accessors for controllers
    public BookService bookService() {
        return bookService;
    }

    public PatronService patronService() {
        return patronService;
    }
}