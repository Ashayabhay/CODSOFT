import java.util.Scanner;

public class MarksCalculator {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of subjects: ");
            int numSubjects = scanner.nextInt();
            
            int[] marks = new int[numSubjects];
            int totalMarks = 0;
            
            for (int i = 0; i < numSubjects; i++) {
                System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
                marks[i] = scanner.nextInt();
                totalMarks += marks[i];
            }
            
            double averagePercentage = (double) totalMarks / numSubjects;
            char grade;
            
            if (averagePercentage >= 90) {
                grade = 'A';
            } else if (averagePercentage >= 80) {
                grade = 'B';
            } else if (averagePercentage >= 70) {
                grade = 'C';
            } else if (averagePercentage >= 60) {
                grade = 'D';
            } else if (averagePercentage >= 50) {
                grade = 'E';
            } else {
                grade = 'F';
            }
            
            System.out.println("\n--- Results ---");
            System.out.println("Total Marks: " + totalMarks);
            System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
            System.out.println("Grade: " + grade);
        }
    }
}
