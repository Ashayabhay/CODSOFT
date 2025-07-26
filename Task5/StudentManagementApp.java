import java.util.Scanner;

/**
 * Main application class for Student Management System
 * Provides console-based user interface for interacting with the system
 */
public class StudentManagementApp {
    private StudentManagementSystem sms;
    private Scanner scanner;
    private boolean running;
    
    public StudentManagementApp() {
        sms = new StudentManagementSystem();
        scanner = new Scanner(System.in);
        running = true;
    }
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        StudentManagementApp app = new StudentManagementApp();
        app.run();
    }
    
    /**
     * Main application loop
     */
    public void run() {
        displayWelcomeMessage();
        
        while (running) {
            displayMenu();
            handleUserChoice();
        }
        
        displayGoodbyeMessage();
        scanner.close();
    }
    
    /**
     * Display welcome message
     */
    private void displayWelcomeMessage() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("    WELCOME TO STUDENT MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("    Manage your students efficiently!");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Display main menu
     */
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Add New Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Search Student");
        System.out.println("4. Edit Student Information");
        System.out.println("5. Display All Students");
        System.out.println("6. Show Statistics");
        System.out.println("7. Exit Application");
        System.out.println("=".repeat(40));
        System.out.print("Enter your choice (1-7): ");
    }
    
    /**
     * Handle user menu choice
     */
    private void handleUserChoice() {
        try {
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    sms.addStudent();
                    break;
                case "2":
                    sms.removeStudent();
                    break;
                case "3":
                    sms.searchStudent();
                    break;
                case "4":
                    sms.editStudent();
                    break;
                case "5":
                    sms.displayAllStudents();
                    break;
                case "6":
                    showStatistics();
                    break;
                case "7":
                    confirmExit();
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 7.");
            }
            
            if (running && !choice.equals("7")) {
                pauseForUser();
            }
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            pauseForUser();
        }
    }
    
    /**
     * Show system statistics
     */
    private void showStatistics() {
        System.out.println("\n=== System Statistics ===");
        System.out.println("Total Students: " + sms.getStudentCount());
        
        if (sms.getStudentCount() == 0) {
            System.out.println("No students in the system.");
        } else {
            System.out.println("Data file: students.dat");
            System.out.println("System Status: Active");
        }
        System.out.println("========================");
    }
    
    /**
     * Confirm exit from application
     */
    private void confirmExit() {
        System.out.print("\nAre you sure you want to exit? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("y") || confirmation.equals("yes")) {
            running = false;
        } else {
            System.out.println("Returning to main menu...");
        }
    }
    
    /**
     * Pause for user to read output
     */
    private void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Display goodbye message
     */
    private void displayGoodbyeMessage() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    Thank you for using Student Management System!");
        System.out.println("    Your data has been saved automatically.");
        System.out.println("=".repeat(50));
        System.out.println("    Goodbye!");
        System.out.println("=".repeat(50));
    }
}