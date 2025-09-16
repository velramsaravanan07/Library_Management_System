package com.library.impl;

import com.library.dto.Patron;
import com.library.repoistory.PatronRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryPatronRepository implements PatronRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryPatronRepository.class.getName());
    private final Map<Long, Patron> patronMap = new HashMap<>();
    private long nextId = 1;

    @Override
    public void addPatron(Patron patron) {
        patron.setPatronId(nextId);
        patronMap.put(nextId, patron);
        LOGGER.log(Level.INFO, "Patron added with ID: " + nextId);
        nextId++;
    }

    @Override
    public Patron updatePatron(Patron patron) {
        if (patronMap.containsKey(patron.getPatronId())) {
            patronMap.put(patron.getPatronId(), patron);
            LOGGER.log(Level.INFO, "Patron updated with ID: " + patron.getPatronId());
            return patron;
        }
        LOGGER.log(Level.WARNING, "Patron not found for update with ID: " + patron.getPatronId());
        return null;
    }

    @Override
    public String removePatron(long patronId) {
        if (patronMap.containsKey(patronId)) {
            Patron removedPatron = patronMap.remove(patronId);
            LOGGER.log(Level.INFO, "Patron removed with ID: " + patronId);
            return "Patron removed successfully: " + removedPatron.getName();
        }
        LOGGER.log(Level.WARNING, "Patron not found for removal with ID: " + patronId);
        return "Patron not found with ID: " + patronId;
    }

    @Override
    public Patron getPatronById(long patronId) {
        return patronMap.get(patronId);
    }

    @Override
    public List<Patron> findAllPatrons() {
        List<Patron> allPatrons = new ArrayList<>(patronMap.values());
        LOGGER.log(Level.INFO, "Retrieved " + allPatrons.size() + " patrons");
        return allPatrons;
    }
}