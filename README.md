# Library Management System 📚

A Java-based Library Management System using various design patterns like Factory, Strategy, and Observer.




A Java-based Library Management System designed to manage books, patrons, and library branches efficiently. The system leverages design patterns such as Factory (for repository instantiation), Strategy (for recommendation algorithms), and Observer (for notifications) to ensure scalability and maintainability.

## 📘 Class Diagram

![LMS Class Diagram](Library_Management_System
/images/LMS_Class_Daigram.png)

The class diagram illustrates the relationships between key components:





DTOs: Book, Patron, Branch, BorrowingRecord, and Reservation represent data entities.



Repositories: BookRepository, PatronRepository, BranchRepository, BorrowingRepository, and ReservationRepository (with in-memory implementations) handle data access.



Services: BookService, PatronService, LendingService, BranchService, RecommendationService, and NotificationService encapsulate business logic.



Facade: LibraryManagementFacade provides a unified interface to services.



Controllers: UnifiedLibraryController, BookController, PatronController, LendingController, BranchController, and RecommendationController manage user interactions.



Relationships: Includes aggregation (e.g., Branch to Book via bookIds), dependency (e.g., controllers to facade), and implementation (e.g., in-memory repositories to interfaces).

## ✨ Features


Book Issuing and Return: Manage book borrowing and returns with status tracking.



Patron and Librarian Management: Add, update, or remove patrons and librarian accounts.



Multi-Branch Support: Handle multiple library branches, including book transfers between branches using the transferBook method (with ISBN as long).



Book Reservation with Notification: Reserve books and receive notifications via the Observer pattern.



Book Recommendations: Provide personalized recommendations using the Strategy pattern.

