package com.library.controller;

import com.library.dto.Book;
import com.library.helper.LibraryManagementFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecommendationController {
    private static final Logger LOGGER = Logger.getLogger(RecommendationController.class.getName());
    private final LibraryManagementFacade facade;

    public RecommendationController(LibraryManagementFacade facade) {
        this.facade = facade;
        LOGGER.log(Level.INFO, "RecommendationController initialized");
    }

    public void getRecommendations(long patronId, int maxRecommendations) {
        List<Book> recommendations = facade.getRecommendations(patronId, maxRecommendations);
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available for patron ID: " + patronId);
        } else {
            System.out.println("Recommendations for Patron ID " + patronId + ":");
            for (Book book : recommendations) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }
}