// Developer: Paul
// Role: Menu Developer
// Description: Handles menu display and user navigation
import java.sql.Connection;
import java.util.Scanner;

/**
 * Main Entry Point
 * Handles menu display and user navigation
 * All methods are static (no OOP)
 */
public class Main {

    private static final String DIVIDER = "============================================================";

    public static void main(String[] args) {
        System.out.println(DIVIDER);
        System.out.println("  STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("  Connecting to database...");
        System.out.println(DIVIDER);

        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("[ERROR] Could not connect to database. Exiting.");
            return;
        }

        int choice = -1;
        while (choice != 0) {
            showMenu();
            choice = InputHelper.getIntInput("Enter your choice (0-7): ");
            switch (choice) {
                case 1: addStudent();        break;
                case 2: viewAllStudents();   break;
                case 3: searchById();        break;
                case 4: searchByName();      break;
                case 5: updateStudent();     break;
                case 6: deleteStudent();     break;
                case 7: transferCourse();    break;
                case 0:
                    System.out.println("\nGoodbye! Thank you for using the system.");
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please enter 0-7.");
            }
        }

        DBConnection.closeConnection();
    }

    // ------------------------------------------------------------------
    // MENU
    // ------------------------------------------------------------------
    private static void showMenu() {
        System.out.println("\n" + DIVIDER);
        System.out.println("          MAIN MENU");
        System.out.println(DIVIDER);
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Search Student by Name");
        System.out.println("5. Update Student Information");
        System.out.println("6. Delete Student");
        System.out.println("7. Transfer Student to Another Course");
        System.out.println("0. Exit");
        System.out.println(DIVIDER);
    }

    // ------------------------------------------------------------------
    // PAUSE - waits for ENTER before returning to menu (back button)
    // ------------------------------------------------------------------
    private static void pauseAndReturn() {
        System.out.println("\n" + DIVIDER);
        System.out.print("Press ENTER to return to main menu...");
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (Exception e) {
            // ignore
        }
    }

    // ------------------------------------------------------------------
    // ADD STUDENT
    // ------------------------------------------------------------------
    private static void addStudent() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         ADD NEW STUDENT");
        System.out.println(DIVIDER);

        String name   = InputHelper.getStringInput("Enter student name: ");
        String email  = InputHelper.getEmailInput("Enter email address: ");
        int    age    = InputHelper.getAgeInput("Enter age: ");
        String course = InputHelper.getCourseInput("Enter course: ");
        double gpa    = InputHelper.getDoubleInput("Enter GPA (0.0 - 4.0): ");
        String date   = InputHelper.getDateInput("Enter enrollment date");

        CRUDOperations.createStudent(name, email, age, course, gpa, date);

        pauseAndReturn();
    }

    // ------------------------------------------------------------------
    // VIEW ALL STUDENTS
    // ------------------------------------------------------------------
    private static void viewAllStudents() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         ALL STUDENTS");
        System.out.println(DIVIDER);

        CRUDOperations.readAllStudents();

        pauseAndReturn();
    }

    // ------------------------------------------------------------------
    // SEARCH BY ID
    // ------------------------------------------------------------------
    private static void searchById() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         SEARCH STUDENT BY ID");
        System.out.println(DIVIDER);

        int id = InputHelper.getIntInput("Enter student ID: ");
        CRUDOperations.readStudentById(id);

        pauseAndReturn();
    }

    // ------------------------------------------------------------------
    // SEARCH BY NAME
    // ------------------------------------------------------------------
    private static void searchByName() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         SEARCH STUDENT BY NAME");
        System.out.println(DIVIDER);

        String name = InputHelper.getStringInput("Enter student name to search: ");
        CRUDOperations.readStudentByName(name);

        pauseAndReturn();
    }

    // ------------------------------------------------------------------
    // UPDATE STUDENT
    // ------------------------------------------------------------------
    private static void updateStudent() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         UPDATE STUDENT");
        System.out.println(DIVIDER);

        int id = InputHelper.getIntInput("Enter student ID to update: ");

        System.out.println("\nCurrent data:");
        CRUDOperations.readStudentById(id);

        System.out.println("Enter new details:");
        String name   = InputHelper.getStringInput("Enter new student name: ");
        String email  = InputHelper.getEmailInput("Enter new email address: ");
        int    age    = InputHelper.getAgeInput("Enter new age: ");
        String course = InputHelper.getCourseInput("Enter new course: ");
        double gpa    = InputHelper.getDoubleInput("Enter new GPA (0.0 - 4.0): ");

        CRUDOperations.updateStudent(id, name, email, age, course, gpa);

        pauseAndReturn();
    }

    // ------------------------------------------------------------------
    // DELETE STUDENT
    // ------------------------------------------------------------------
    private static void deleteStudent() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         DELETE STUDENT");
        System.out.println(DIVIDER);

        int id = InputHelper.getIntInput("Enter student ID to delete: ");

        System.out.println("\nStudent to be deleted:");
        CRUDOperations.readStudentById(id);

        String confirm = InputHelper.getStringInput("\nAre you sure? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes")) {
            CRUDOperations.deleteStudent(id);
        } else {
            System.out.println("[!] Deletion cancelled.");
        }

        pauseAndReturn();
    }

    // ------------------------------------------------------------------
    // TRANSFER COURSE
    // ------------------------------------------------------------------
    private static void transferCourse() {
        System.out.println("\n" + DIVIDER);
        System.out.println("         TRANSFER STUDENT TO ANOTHER COURSE");
        System.out.println(DIVIDER);

        int    id        = InputHelper.getIntInput("Enter student ID: ");
        String newCourse = InputHelper.getCourseInput("Enter new course: ");

        CRUDOperations.transferStudentCourse(id, newCourse);

        pauseAndReturn();
    }
}
