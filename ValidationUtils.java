import java.util.regex.Pattern;

/**
 * Utility class for input validation
 * Contains static methods for validating various types of input
 */
public class ValidationUtils {
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    // Phone number validation pattern (10 digits)
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\d{10}$");
    
    // Name validation pattern (only letters and spaces)
    private static final Pattern NAME_PATTERN = 
        Pattern.compile("^[A-Za-z\\s]+$");
    
    // Roll number pattern (alphanumeric)
    private static final Pattern ROLL_NUMBER_PATTERN = 
        Pattern.compile("^[A-Za-z0-9]+$");
    
    /**
     * Validate email address
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate phone number (10 digits)
     * @param phoneNumber Phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
    }
    
    /**
     * Validate name (only letters and spaces)
     * @param name Name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && 
               NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    /**
     * Validate roll number (alphanumeric)
     * @param rollNumber Roll number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidRollNumber(String rollNumber) {
        return rollNumber != null && !rollNumber.trim().isEmpty() && 
               ROLL_NUMBER_PATTERN.matcher(rollNumber.trim()).matches();
    }
    
    /**
     * Validate age
     * @param age Age to validate
     * @return true if valid (between 1 and 150), false otherwise
     */
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 150;
    }
    
    /**
     * Check if string is null or empty
     * @param str String to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Sanitize string input (trim and handle null)
     * @param input Input string
     * @return Sanitized string or empty string if null
     */
    public static String sanitizeInput(String input) {
        return input == null ? "" : input.trim();
    }
    
    /**
     * Validate grade format
     * @param grade Grade to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidGrade(String grade) {
        if (isNullOrEmpty(grade)) {
            return false;
        }
        
        String trimmedGrade = grade.trim().toUpperCase();
        
        // Check for letter grades (A, B, C, D, F with optional + or -)
        if (trimmedGrade.matches("^[ABCDF][+-]?$")) {
            return true;
        }
        
        // Check for numeric grades (0-100)
        try {
            double numericGrade = Double.parseDouble(trimmedGrade);
            return numericGrade >= 0 && numericGrade <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Format phone number for display
     * @param phoneNumber Raw phone number
     * @return Formatted phone number (XXX-XXX-XXXX)
     */
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 10) {
            return phoneNumber;
        }
        
        return phoneNumber.substring(0, 3) + "-" + 
               phoneNumber.substring(3, 6) + "-" + 
               phoneNumber.substring(6);
    }
    
    /**
     * Capitalize first letter of each word
     * @param input Input string
     * @return Capitalized string
     */
    public static String capitalizeWords(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        
        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    result.append(words[i].substring(1));
                }
            }
        }
        
        return result.toString();
    }
}