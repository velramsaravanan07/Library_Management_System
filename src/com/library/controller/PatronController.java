package com.library.controller;

import com.library.dto.Patron;
import com.library.helper.LibraryManagementFacade;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatronController {
    private static final Logger LOGGER = Logger.getLogger(PatronController.class.getName());
    private final LibraryManagementFacade facade;
    private final Scanner scanner;

    public PatronController(LibraryManagementFacade facade) {
        if (facade == null) {
            throw new IllegalArgumentException("LibraryManagementFacade cannot be null");
        }
        this.facade = facade;
        this.scanner = new Scanner(System.in);
        LOGGER.log(Level.INFO, "PatronController initialized");
    }

    public void start() {
        while (true) {
            displayMenu();
            int choice = getChoice();

            try {
                processChoice(choice);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error in processChoice: " + e.getMessage());
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Patron Management System ===");
        System.out.println("1. Add patron");
        System.out.println("2. Update patron");
        System.out.println("3. Remove patron");
        System.out.println("4. Get patron by ID");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1: addPatron(); break;
            case 2: updatePatron(); break;
            case 3: removePatron(); break;
            case 4: getPatronById(); break;
            case 5:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addPatron() {
        System.out.print("Enter patron name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patron email: ");
        String email = scanner.nextLine();

        Patron patron = new Patron(0, name, email); // ID set to 0, assuming repo assigns it
        facade.addPatron(patron);
        // Note: PatronService already prints success message via facade
        LOGGER.log(Level.INFO, "Patron added: " + name);
    }

    private void updatePatron() {
        System.out.print("Enter patron ID to update: ");
        long patronId;
        try {
            patronId = scanner.nextLong();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid ID format.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        Patron patron = new Patron(patronId, name, email);
        Patron updated = facade.updatePatron(patron);
        if (updated != null) {
            System.out.println("Patron updated successfully: " + updated);
            LOGGER.log(Level.INFO, "Patron updated: ID " + patronId);
        } else {
            System.out.println("Patron not found or update failed.");
            LOGGER.log(Level.WARNING, "Failed to update patron ID: " + patronId);
        }
    }

    private void removePatron() {
        System.out.print("Enter patron ID to remove: ");
        long patronId;
        try {
            patronId = scanner.nextLong();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid ID format.");
            return;
        }

        String result = facade.removePatron(patronId);
        if (!result.isEmpty()) {
            System.out.println(result);
            LOGGER.log(Level.INFO, "Patron removed: ID " + patronId);
        } else {
            System.out.println("Patron not found or removal failed.");
            LOGGER.log(Level.WARNING, "Failed to remove patron ID: " + patronId);
        }
    }

    private void getPatronById() {
        System.out.print("Enter patron ID: ");
        long patronId;
        try {
            patronId = scanner.nextLong();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid ID format.");
            return;
        }

        Patron patron = facade.findPatronById(patronId);
        displayResult(patron);
    }

    private void displayResult(Patron patron) {
        if (patron != null) {
            System.out.println("Found: " + patron);
            LOGGER.log(Level.INFO, "Retrieved patron: " + patron.getName());
        } else {
            System.out.println("Patron not found.");
            LOGGER.log(Level.WARNING, "Patron not found for ID");
        }
    }
}