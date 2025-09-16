package com.library.controller;

import com.library.dto.Book;
import com.library.dto.Branch;
import com.library.dto.Patron;
import com.library.helper.LibraryManagementFacade;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnifiedLibraryController {
    private static final Logger LOGGER = Logger.getLogger(UnifiedLibraryController.class.getName());
    private final LibraryManagementFacade facade;
    private final Scanner scanner;

    public UnifiedLibraryController() {
        this.facade = new LibraryManagementFacade();
        this.scanner = new Scanner(System.in);
        LOGGER.log(Level.INFO, "UnifiedLibraryController initialized with facade");
    }

    public static void main(String[] args) {
        UnifiedLibraryController controller = new UnifiedLibraryController();
        controller.start();
    }

    public void start() {
        while (true) {
            displayMainMenu();
            int choice = getChoice();

            try {
                if (!processMainChoice(choice)) {
                    break;
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error in processMainChoice: " + e.getMessage());
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
        LOGGER.log(Level.INFO, "Unified Library Management System terminated");
    }

    private void displayMainMenu() {
        System.out.println("\n=== Unified Library Management System ===");
        System.out.println("1. Book Management");
        System.out.println("2. Patron Management");
        System.out.println("3. Lending Management");
        System.out.println("4. Branch Management");
        System.out.println("5. Recommendation System");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    private boolean processMainChoice(int choice) {
        switch (choice) {
            case 1:
                bookManagement();
                return true;
            case 2:
                patronManagement();
                return true;
            case 3:
                lendingManagement();
                return true;
            case 4:
                branchManagement();
                return true;
            case 5:
                recommendationManagement();
                return true;
            case 6:
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
                return true;
        }
    }

    private void bookManagement() {
        while (true) {
            displayBookMenu();
            int choice = getChoice();
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter ISBN (numeric): ");
                        String isbnInput = scanner.nextLine();
                        long isbn = Long.parseLong(isbnInput.replaceAll("[^0-9]", ""));
                        System.out.print("Enter publication year: ");
                        int year = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter branch ID: ");
                        long branchId = Long.parseLong(scanner.nextLine());
                        new BookController(facade).addBook(title, author, isbnInput, year, branchId);
                        break;
                    case 2:
                        System.out.print("Search by (1=Title, 2=Author, 3=ISBN): ");
                        int searchType = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter search term: ");
                        String term = scanner.nextLine();
                        new BookController(facade).searchBook(searchType, term);
                        break;
                    case 3:
                        System.out.print("Enter book ID to remove: ");
                        long bookId = Long.parseLong(scanner.nextLine());
                        String result = facade.removeBook(bookId);
                        System.out.println(result);
                        break;
                    default:
                        System.out.println("Invalid book choice.");
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid input format: " + e.getMessage());
                System.out.println("Error: Invalid input format.");
            }
        }
    }

    private void displayBookMenu() {
        System.out.println("\n=== Book Management ===");
        System.out.println("1. Add book");
        System.out.println("2. Search book");
        System.out.println("3. Remove book");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
    }

    private void patronManagement() {
        new PatronController(facade).start();
    }

    private void lendingManagement() {
        while (true) {
            displayLendingMenu();
            int choice = getChoice();
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter patron ID: ");
                        long patronId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter book ID: ");
                        long bookId = Long.parseLong(scanner.nextLine());
                        new LendingController(facade).borrowBook(patronId, bookId);
                        break;
                    case 2:
                        System.out.print("Enter patron ID: ");
                        patronId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter book ID: ");
                        bookId = Long.parseLong(scanner.nextLine());
                        new LendingController(facade).returnBook(patronId, bookId);
                        break;
                    case 3:
                        System.out.print("Enter patron ID: ");
                        patronId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter book ID: ");
                        bookId = Long.parseLong(scanner.nextLine());
                        new LendingController(facade).reserveBook(patronId, bookId);
                        break;
                    case 4:
                        new LendingController(facade).viewOverdueBooks();
                        break;
                    case 5:
                        System.out.print("Enter patron ID: ");
                        patronId = Long.parseLong(scanner.nextLine());
                        String info = facade.getPatronBorrowingInfo(patronId);
                        System.out.println("Borrowing info: " + info);
                        break;
                    case 6:
                        System.out.print("Enter patron ID: ");
                        patronId = Long.parseLong(scanner.nextLine());
                        boolean canBorrow = facade.canPatronBorrowMoreBooks(patronId);
                        if (canBorrow) {
                            System.out.println("Patron can borrow more books (limit: 5 active borrowings).");
                        } else {
                            System.out.println("Patron has reached the borrowing limit (5 active borrowings).");
                        }
                        break;
                    default:
                        System.out.println("Invalid lending choice.");
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid input format: " + e.getMessage());
                System.out.println("Error: Invalid input format.");
            }
        }
    }

    private void displayLendingMenu() {
        System.out.println("\n=== Lending Management ===");
        System.out.println("1. Borrow book");
        System.out.println("2. Return book");
        System.out.println("3. Reserve book");
        System.out.println("4. View overdue books");
        System.out.println("5. Get patron borrowing info");
        System.out.println("6. Check borrowing limit");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
    }

    private void branchManagement() {
        while (true) {
            displayBranchMenu();
            int choice = getChoice();
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter branch name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter branch address: ");
                        String address = scanner.nextLine();
                        new BranchController(facade).addBranch(name, address);
                        break;
                    case 2:
                        System.out.print("Enter book ID: ");
                        long bookId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter from branch ID: ");
                        long fromBranchId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter to branch ID: ");
                        long toBranchId = Long.parseLong(scanner.nextLine());
                        new BranchController(facade).transferBook(bookId, fromBranchId, toBranchId);
                        break;
                    default:
                        System.out.println("Invalid branch choice.");
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid input format: " + e.getMessage());
                System.out.println("Error: Invalid input format.");
            }
        }
    }

    private void displayBranchMenu() {
        System.out.println("\n=== Branch Management ===");
        System.out.println("1. Add branch");
        System.out.println("2. Transfer book");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
    }

    private void recommendationManagement() {
        while (true) {
            displayRecommendationMenu();
            int choice = getChoice();
            if (choice == 0) break;

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter patron ID: ");
                        long patronId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter max recommendations: ");
                        int maxRecs = Integer.parseInt(scanner.nextLine());
                        new RecommendationController(facade).getRecommendations(patronId, maxRecs);
                        break;
                    default:
                        System.out.println("Invalid recommendation choice.");
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid input format: " + e.getMessage());
                System.out.println("Error: Invalid input format.");
            }
        }
    }

    private void displayRecommendationMenu() {
        System.out.println("\n=== Recommendation System ===");
        System.out.println("1. Get recommendations");
        System.out.println("0. Back to main menu");
        System.out.print("Enter your choice: ");
    }
}