package com.library.service;
import com.library.dto.Book;
import com.library.dto.BorrowingRecord;
import com.library.dto.Patron;
import com.library.repoistory.BookRepository;
import com.library.repoistory.BorrowingRepository;
import com.library.repoistory.PatronRepository;

import java.util.*;

public class RecommendationService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRepository borrowingRepository;

    public RecommendationService(BookRepository bookRepository, PatronRepository patronRepository, BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRepository = borrowingRepository;
    }

    public List getRecommendations(long patronId, int maxRecommendations) {
        Patron patron = patronRepository.getPatronById(patronId);
        if (patron == null) {
            return Collections.emptyList();
        }

        List history = borrowingRepository.findByPatronId(patronId);
        List recommendations = new ArrayList();
        Set borrowedBookIds = new HashSet();
        for (Object record : history) {
            BorrowingRecord borrowingRecord = (BorrowingRecord) record;
            borrowedBookIds.add(borrowingRecord.getBookId());
        }

        // Recommend popular books that haven't been borrowed and are available
        List popularBooks = getPopularBooks();
        for (Object bookObj : popularBooks) {
            Book book = (Book) bookObj;
            if (book.isAvailable() && !borrowedBookIds.contains(book.getBookid()) && !recommendations.contains(book)) {
                recommendations.add(book);
            }
            if (recommendations.size() >= maxRecommendations) {
                break;
            }
        }

        // Limit recommendations to maxRecommendations
        List finalRecommendations = new ArrayList();
        for (int i = 0; i < Math.min(recommendations.size(), maxRecommendations); i++) {
            finalRecommendations.add(recommendations.get(i));
        }
        return finalRecommendations;
    }

    private List getPopularBooks() {
        // In a real system, this would be based on borrowing statistics
        // For now, return random available books using BookRepo
        List allBooks = bookRepository.findAvailableBooks();
        Collections.shuffle(allBooks);
        return allBooks;
    }
}