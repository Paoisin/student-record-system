// Developer: Hector
// Role: Validation Engineer
// Description: Handles all input validation and error checking
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Input Validation and Helper
 * All input methods in one place - prevents code repetition
 */
public class InputHelper {
    
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Get valid integer input
     * Keeps asking until user enters a valid number
     */
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("✗ Input cannot be empty");
                    continue;
                }
                
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid number");
            }
        }
    }
    
    /**
     * Get valid double input (for GPA)
     */
    public static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("✗ Input cannot be empty");
                    continue;
                }
                
                double value = Double.parseDouble(input);
                
                if (value < 0.0 || value > 4.0) {
                    System.out.println("✗ GPA must be between 0.0 and 4.0");
                    continue;
                }
                
                return value;
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid decimal number");
            }
        }
    }
    
    /**
     * Get valid string input (not empty)
     */
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("✗ Input cannot be empty");
                continue;
            }
            
            if (input.length() > 100) {
                System.out.println("✗ Input too long (max 100 characters)");
                continue;
            }
            
            return input;
        }
    }
    
    /**
     * Get valid email input
     */
    public static String getEmailInput(String prompt) {
        while (true) {
            String email = getStringInput(prompt);
            
            if (isValidEmail(email)) {
                return email;
            }
            
            System.out.println("✗ Invalid email format (use: example@domain.com)");
        }
    }
    
    /**
     * Get valid age input
     */
    public static int getAgeInput(String prompt) {
        while (true) {
            int age = getIntInput(prompt);
            
            if (age < 15 || age > 80) {
                System.out.println("✗ Age must be between 15 and 80");
                continue;
            }
            
            return age;
        }
    }
    
    /**
     * Get valid course input
     */
    public static String getCourseInput(String prompt) {
        String[] validCourses = {"CS", "IT", "IS", "EN", "ME", "CE"};
        
        while (true) {
            System.out.println("Available courses: CS (Computer Science), IT (Information Technology), " +
                             "IS (Information Systems), EN (Engineering), ME (Mechanical), CE (Civil)");
            String course = getStringInput(prompt).toUpperCase();
            
            boolean isValid = false;
            for (String valid : validCourses) {
                if (course.equals(valid)) {
                    isValid = true;
                    break;
                }
            }
            
            if (isValid) {
                return course;
            }
            
            System.out.println("✗ Invalid course. Please choose from the list above");
        }
    }
    
    /**
     * Get valid date input (YYYY-MM-DD format)
     */
    public static String getDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        while (true) {
            System.out.print(prompt + " (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                
                // Check if date is not in the future
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("✗ Enrollment date cannot be in the future");
                    continue;
                }
                
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("✗ Invalid date format. Use yyyy-MM-dd (e.g., 2024-01-15)");
            }
        }
    }
    
    /**
     * Simple email validation
     */
    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Close scanner
     */
    public static void closeScanner() {
        scanner.close();
    }
}
