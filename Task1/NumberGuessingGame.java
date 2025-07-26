import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Random random = new Random();
            
            final int min = 1;
            final int max = 100;
            final int maxAttempts = 7;
            
            int roundsPlayed = 0;
            int roundsWon = 0;
            int totalAttempts = 0;
            
            boolean playAgain = true;
            
            System.out.println("=== Number Guessing Game ===");
            System.out.println("Guess the number between " + min + " and " + max + ".");
            System.out.println("You have " + maxAttempts + " attempts per round.\n");
            
            while (playAgain) {
                int numberToGuess = random.nextInt(max - min + 1) + min;
                int attempts = 0;
                boolean guessedCorrectly = false;
                
                roundsPlayed++;
                
                while (attempts < maxAttempts) {
                    System.out.print("Enter your guess (Attempt " + (attempts + 1) + "/" + maxAttempts + "): ");
                    int guess;
                    
                    try {
                        guess = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        continue;
                    }
                    
                    attempts++;
                    totalAttempts++;
                    
                    if (guess == numberToGuess) {
                        System.out.println(" Correct! You guessed the number in " + attempts + " attempt(s).\n");
                        guessedCorrectly = true;
                        roundsWon++;
                        break;
                    } else if (guess < numberToGuess) {
                        System.out.println("Too low!");
                    } else {
                        System.out.println("Too high!");
                    }
                }
                
                if (!guessedCorrectly) {
                    System.out.println("Out of attempts! The number was: " + numberToGuess + "\n");
                }
                
                System.out.print("Do you want to play another round? (Y/N): ");
                String response = sc.nextLine().trim().toLowerCase();
                playAgain = response.equals("Y") || response.equals("yes");
            }
            
            System.out.println("\n=== Game Over ===");
            System.out.println("Rounds played: " + roundsPlayed);
            System.out.println("Rounds won: " + roundsWon);
            System.out.println("Total attempts: " + totalAttempts);
            
            if (roundsPlayed > 0) {
                double avgAttempts = (double) totalAttempts / roundsPlayed;
                System.out.printf("Average attempts per round: %.2f%n", avgAttempts);
            }
            
            System.out.println("Thanks for playing!");
        }
    }
}
