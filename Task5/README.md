# 🎓 Student Management System (Console-Based Java Project)

A simple Java console-based application to manage student records. Built as part of my Java internship at **CodSoft**, this project demonstrates core Java concepts such as OOP, validation, modular design, and file I/O operations.

---

## 📁 Project Structure

StudentManagementSystem/
├── Main.java # Application entry point
├── Student.java # POJO class representing a student
├── StudentManager.java # Handles student CRUD operations and file I/O
├── ValidationUtils.java # Utility class for input validation and formatting
├── students.txt # Stores student data persistently

yaml
Copy
Edit

---

## ✅ Features

- Add new students with input validation
- View list of all students
- Update student details (name, email, phone, age, grade)
- Delete student by roll number
- Validate:
  - Email addresses
  - Phone numbers (10-digit)
  - Names (alphabet only)
  - Roll numbers (alphanumeric)
  - Grades (A/B/C/D/F or numeric 0–100)
- Format:
  - Capitalize names
  - Format phone numbers (XXX-XXX-XXXX)
- Store data in a file (`students.txt`) for persistence

---

## 🏃 How to Run the Program

1. **Compile the source files:**
```bash
javac *.java
Run the main class:

bash
Copy
Edit
java Main
🧪 Sample Functionality (Console)
sql
Copy
Edit
===== Student Management System =====
1. Add Student
2. View All Students
3. Update Student
4. Delete Student
5. Exit
Enter your choice:
🛠 Technologies Used
Java SE (Core)

Object-Oriented Programming (OOP)

File Handling (FileReader, FileWriter)

Regular Expressions (Regex)

Exception Handling

🔍 Code Overview
1. Main.java
Provides a menu-driven interface

Handles user interaction and input

2. Student.java
A plain old Java object (POJO)

Contains student attributes and a custom toString() method

3. StudentManager.java
Manages list of students (add, view, update, delete)

Reads from and writes to students.txt

4. ValidationUtils.java
Contains utility methods to validate:

Emails, phone numbers, names, roll numbers, age, grades

Also includes input sanitization and formatting

🚀 Future Enhancements
Add GUI using JavaFX or Swing

Implement search and sort features

Export data to CSV or Excel

Add unit tests using JUnit

Secure data using encryption

👨‍💻 Developed By
Abhay Paathak
Java Full Stack Developer | CodSoft Java Intern

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
