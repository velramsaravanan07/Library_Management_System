package com.library.service;

import com.library.dao.PatronDao;
import com.library.dto.Patron;
import com.library.repoistory.PatronRepository;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PatronService implements PatronDao {
    private static final Logger LOGGER = Logger.getLogger(PatronService.class.getName());
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        if (patronRepository == null) {
            throw new IllegalArgumentException("PatronRepo cannot be null");
        }
        this.patronRepository = patronRepository;
        LOGGER.log(Level.INFO, "PatronService initialized with repository");
    }

    @Override
    public void addPatron(Patron patron) {
        if (patron == null) {
            LOGGER.log(Level.SEVERE, "Attempted to add null patron");
            throw new IllegalArgumentException("Patron cannot be null");
        }

        if (patron.getName() == null || patron.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Patron name cannot be null or empty");
        }

        if (patron.getEmail() == null || patron.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Patron email cannot be null or empty");
        }

        patronRepository.addPatron(patron);
        LOGGER.log(Level.INFO, "Patron added successfully: " + patron.getName());
        System.out.println("Patron added successfully: " + patron);
    }

    @Override
    public Patron updatePatron(Patron patron) {
        if (patron == null) {
            LOGGER.log(Level.SEVERE, "Attempted to update null patron");
            throw new IllegalArgumentException("Patron cannot be null");
        }

        if (patron.getPatronId() <= 0) {
            throw new IllegalArgumentException("Patron ID must be positive");
        }

        Patron updated = patronRepository.updatePatron(patron);
        if (updated != null) {
            LOGGER.log(Level.INFO, "Patron updated successfully: " + patron.getName());
        } else {
            LOGGER.log(Level.WARNING, "Failed to update patron with ID: " + patron.getPatronId());
        }
        return updated;
    }

    @Override
    public String removePatron(long patronId) {
        if (patronId <= 0) {
            throw new IllegalArgumentException("Patron ID must be positive");
        }

        String result = patronRepository.removePatron(patronId);
        LOGGER.log(Level.INFO, "Remove patron operation: " + result);
        return result;
    }

    @Override
    public Patron getPatronById(long patronId) {
        if (patronId <= 0) {
            throw new IllegalArgumentException("Patron ID must be positive");
        }
        return patronRepository.getPatronById(patronId);
    }
}
