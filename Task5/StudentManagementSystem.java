import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * StudentManagementSystem class to manage the collection of students
 * Provides functionality to add, remove, search, and display students
 * Includes file I/O operations for data persistence
 */
public class StudentManagementSystem {
    private List<Student> students;
    private Scanner scanner;
    private static final String DATA_FILE = "students.dat";
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    // Phone number validation pattern (10 digits)
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\d{10}$");
    
    public StudentManagementSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadStudentsFromFile();
    }
    
    /**
     * Add a new student to the system
     */
    public void addStudent() {
        System.out.println("\n=== Add New Student ===");
        
        try {
            String name = getValidatedInput("Enter student name: ", "Name cannot be empty!");
            String rollNumber = getValidatedRollNumber();
            String grade = getValidatedInput("Enter grade: ", "Grade cannot be empty!");
            int age = getValidatedAge();
            String email = getValidatedEmail();
            String phoneNumber = getValidatedPhoneNumber();
            String address = getValidatedInput("Enter address: ", "Address cannot be empty!");
            
            Student student = new Student(name, rollNumber, grade, age, email, phoneNumber, address);
            students.add(student);
            saveStudentsToFile();
            
            System.out.println("Student added successfully!");
            
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    /**
     * Remove a student from the system
     */
    public void removeStudent() {
        System.out.println("\n=== Remove Student ===");
        
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        
        System.out.print("Enter roll number of student to remove: ");
        String rollNumber = scanner.nextLine().trim();
        
        if (rollNumber.isEmpty()) {
            System.out.println("Roll number cannot be empty!");
            return;
        }
        
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber)) {
                studentToRemove = student;
                break;
            }
        }
        
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            saveStudentsToFile();
            System.out.println("Student removed successfully!");
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }
    
    /**
     * Search for a student by roll number or name
     */
    public void searchStudent() {
        System.out.println("\n=== Search Student ===");
        
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        
        System.out.println("1. Search by Roll Number");
        System.out.println("2. Search by Name");
        System.out.print("Choose search option: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                searchByRollNumber();
                break;
            case "2":
                searchByName();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    /**
     * Search student by roll number
     */
    private void searchByRollNumber() {
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine().trim();
        
        if (rollNumber.isEmpty()) {
            System.out.println("Roll number cannot be empty!");
            return;
        }
        
        for (Student student : students) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber)) {
                student.displayDetails();
                return;
            }
        }
        System.out.println("Student with roll number " + rollNumber + " not found.");
    }
    
    /**
     * Search student by name
     */
    private void searchByName() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }
        
        List<Student> foundStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                foundStudents.add(student);
            }
        }
        
        if (foundStudents.isEmpty()) {
            System.out.println("No students found with name containing: " + name);
        } else {
            System.out.println("\nFound " + foundStudents.size() + " student(s):");
            displayStudentHeader();
            for (Student student : foundStudents) {
                System.out.println(student);
            }
        }
    }
    
    /**
     * Display all students in the system
     */
    public void displayAllStudents() {
        System.out.println("\n=== All Students ===");
        
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        
        displayStudentHeader();
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("\nTotal students: " + students.size());
    }
    
    /**
     * Edit an existing student's information
     */
    public void editStudent() {
        System.out.println("\n=== Edit Student ===");
        
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        
        System.out.print("Enter roll number of student to edit: ");
        String rollNumber = scanner.nextLine().trim();
        
        if (rollNumber.isEmpty()) {
            System.out.println("Roll number cannot be empty!");
            return;
        }
        
        Student studentToEdit = null;
        for (Student student : students) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber)) {
                studentToEdit = student;
                break;
            }
        }
        
        if (studentToEdit == null) {
            System.out.println("Student with roll number " + rollNumber + " not found.");
            return;
        }
        
        System.out.println("Current student details:");
        studentToEdit.displayDetails();
        
        System.out.println("\nWhat would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Grade");
        System.out.println("3. Age");
        System.out.println("4. Email");
        System.out.println("5. Phone Number");
        System.out.println("6. Address");
        System.out.print("Choose option: ");
        
        String choice = scanner.nextLine().trim();
        
        try {
            switch (choice) {
                case "1":
                    String newName = getValidatedInput("Enter new name: ", "Name cannot be empty!");
                    studentToEdit.setName(newName);
                    break;
                case "2":
                    String newGrade = getValidatedInput("Enter new grade: ", "Grade cannot be empty!");
                    studentToEdit.setGrade(newGrade);
                    break;
                case "3":
                    int newAge = getValidatedAge();
                    studentToEdit.setAge(newAge);
                    break;
                case "4":
                    String newEmail = getValidatedEmail();
                    studentToEdit.setEmail(newEmail);
                    break;
                case "5":
                    String newPhone = getValidatedPhoneNumber();
                    studentToEdit.setPhoneNumber(newPhone);
                    break;
                case "6":
                    String newAddress = getValidatedInput("Enter new address: ", "Address cannot be empty!");
                    studentToEdit.setAddress(newAddress);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }
            
            saveStudentsToFile();
            System.out.println("Student information updated successfully!");
            
        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }
    
    /**
     * Display header for student table
     */
    private void displayStudentHeader() {
        System.out.println("\n" + "=".repeat(120));
        System.out.printf("%-15s %-12s %-8s %-4s %-25s %-15s %-30s%n", 
                         "Name", "Roll No", "Grade", "Age", "Email", "Phone", "Address");
        System.out.println("=".repeat(120));
    }
    
    /**
     * Load students from file
     */
    @SuppressWarnings("unchecked")
    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Student data loaded successfully. Total students: " + students.size());
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found. Starting with empty student list.");
            students = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data: " + e.getMessage());
            students = new ArrayList<>();
        }
    }
    
    /**
     * Save students to file
     */
    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }
    
    /**
     * Get validated input that cannot be empty
     */
    private String getValidatedInput(String prompt, String errorMessage) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(errorMessage);
            }
        } while (input.isEmpty());
        return input;
    }
    
    /**
     * Get validated roll number (must be unique)
     */
    private String getValidatedRollNumber() {
        String rollNumber;
        do {
            rollNumber = getValidatedInput("Enter roll number: ", "Roll number cannot be empty!");
            
            // Check if roll number already exists
            boolean exists = false;
            for (Student student : students) {
                if (student.getRollNumber().equalsIgnoreCase(rollNumber)) {
                    exists = true;
                    break;
                }
            }
            
            if (exists) {
                System.out.println("Roll number already exists! Please enter a different roll number.");
                rollNumber = "";
            }
        } while (rollNumber.isEmpty());
        
        return rollNumber;
    }
    
    /**
     * Get validated age
     */
    private int getValidatedAge() {
        int age;
        do {
            try {
                System.out.print("Enter age: ");
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age <= 0 || age > 150) {
                    System.out.println("Please enter a valid age (1-150)!");
                    age = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for age!");
                age = -1;
            }
        } while (age == -1);
        
        return age;
    }
    
    /**
     * Get validated email
     */
    private String getValidatedEmail() {
        String email;
        do {
            email = getValidatedInput("Enter email: ", "Email cannot be empty!");
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Please enter a valid email address!");
                email = "";
            }
        } while (email.isEmpty());
        
        return email;
    }
    
    /**
     * Get validated phone number
     */
    private String getValidatedPhoneNumber() {
        String phoneNumber;
        do {
            phoneNumber = getValidatedInput("Enter phone number (10 digits): ", "Phone number cannot be empty!");
            if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
                System.out.println("Please enter a valid 10-digit phone number!");
                phoneNumber = "";
            }
        } while (phoneNumber.isEmpty());
        
        return phoneNumber;
    }
    
    /**
     * Get the number of students in the system
     */
    public int getStudentCount() {
        return students.size();
    }
}