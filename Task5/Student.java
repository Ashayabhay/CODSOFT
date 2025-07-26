import java.io.Serializable;

/**
 * Student class to represent individual students
 * Implements Serializable for file storage functionality
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String rollNumber;
    private String grade;
    private int age;
    private String email;
    private String phoneNumber;
    private String address;
    
    // Default constructor
    public Student() {
    }
    
    // Parameterized constructor
    public Student(String name, String rollNumber, String grade, int age, String email, String phoneNumber, String address) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public String getRollNumber() {
        return rollNumber;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    // Setter methods
    public void setName(String name) {
        this.name = name;
    }
    
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    // toString method for displaying student information
    @Override
    public String toString() {
        return String.format("%-15s %-12s %-8s %-4d %-25s %-15s %-30s", 
                           name, rollNumber, grade, age, email, phoneNumber, address);
    }
    
    // Method to display student details in a formatted way
    public void displayDetails() {
        System.out.println("=== Student Details ===");
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Grade: " + grade);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("=====================");
    }
    
    // equals method for comparing students (based on roll number)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return rollNumber != null ? rollNumber.equals(student.rollNumber) : student.rollNumber == null;
    }
    
    // hashCode method
    @Override
    public int hashCode() {
        return rollNumber != null ? rollNumber.hashCode() : 0;
    }
}