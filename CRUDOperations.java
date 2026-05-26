// Developer: Prince
// Role: Backend Developer
// Description: Handles all CRUD database operations

import java.sql.*;

/**
 * CRUD Operations
 * All database queries in one place
 * Uses PreparedStatement to prevent SQL injection
 */
public class CRUDOperations {
    
    // ============= CREATE (INSERT) =============
    /**
     * Add new student to database
     * @return true if successful, false otherwise
     */
    public static boolean createStudent(String name, String email, int age, 
                                       String course, double gpa, String enrollmentDate) {
        String sql = "INSERT INTO students (student_name, email, age, course, gpa, enrollment_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            // Use ? placeholders to prevent SQL injection
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, age);
            pstmt.setString(4, course);
            pstmt.setDouble(5, gpa);
            pstmt.setString(6, enrollmentDate);
            
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("✓ Student added successfully!");
                return true;
            }
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("✗ Email already exists!");
            } else {
                System.out.println("✗ Error adding student: " + e.getMessage());
            }
        } finally {
            closeResources(null, pstmt);
        }
        
        return false;
    }
    
    // ============= READ (SELECT) =============
    /**
     * Display all students
     */
    public static void readAllStudents() {
        String sql = "SELECT * FROM students ORDER BY student_id";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (!rs.isBeforeFirst()) {
                System.out.println("✗ No students found in database");
                return;
            }
            
            System.out.println("\n" + "=".repeat(100));
            System.out.printf("%-5s | %-20s | %-25s | %-5s | %-8s | %-5s | %-12s\n",
                            "ID", "Name", "Email", "Age", "Course", "GPA", "Enrollment Date");
            System.out.println("=".repeat(100));
            
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-25s | %-5d | %-8s | %-5.2f | %-12s\n",
                                rs.getInt("student_id"),
                                rs.getString("student_name"),
                                rs.getString("email"),
                                rs.getInt("age"),
                                rs.getString("course"),
                                rs.getDouble("gpa"),
                                rs.getDate("enrollment_date"));
            }
            
            System.out.println("=".repeat(100) + "\n");
            
        } catch (SQLException e) {
            System.out.println("✗ Error reading students: " + e.getMessage());
        } finally {
            closeResources(rs, stmt);
        }
    }
    
    /**
     * Search student by ID
     */
    public static void readStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            
            rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("✗ Student ID " + studentId + " not found");
                return;
            }
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Student ID: " + rs.getInt("student_id"));
            System.out.println("Name: " + rs.getString("student_name"));
            System.out.println("Email: " + rs.getString("email"));
            System.out.println("Age: " + rs.getInt("age"));
            System.out.println("Course: " + rs.getString("course"));
            System.out.println("GPA: " + rs.getDouble("gpa"));
            System.out.println("Enrollment Date: " + rs.getDate("enrollment_date"));
            System.out.println("=".repeat(60) + "\n");
            
        } catch (SQLException e) {
            System.out.println("✗ Error searching student: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
    }
    
    /**
     * Search student by name (partial match)
     */
    public static void readStudentByName(String name) {
        String sql = "SELECT * FROM students WHERE student_name LIKE ? ORDER BY student_id";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            
            rs = pstmt.executeQuery();
            
            if (!rs.isBeforeFirst()) {
                System.out.println("✗ No students found with name containing: " + name);
                return;
            }
            
            System.out.println("\n" + "=".repeat(100));
            System.out.printf("%-5s | %-20s | %-25s | %-5s | %-8s | %-5s | %-12s\n",
                            "ID", "Name", "Email", "Age", "Course", "GPA", "Enrollment Date");
            System.out.println("=".repeat(100));
            
            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-25s | %-5d | %-8s | %-5.2f | %-12s\n",
                                rs.getInt("student_id"),
                                rs.getString("student_name"),
                                rs.getString("email"),
                                rs.getInt("age"),
                                rs.getString("course"),
                                rs.getDouble("gpa"),
                                rs.getDate("enrollment_date"));
            }
            
            System.out.println("=".repeat(100) + "\n");
            
        } catch (SQLException e) {
            System.out.println("✗ Error searching students: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
    }
    
    // ============= UPDATE =============
    /**
     * Update student information
     */
    public static boolean updateStudent(int studentId, String name, String email, 
                                       int age, String course, double gpa) {
        String sql = "UPDATE students SET student_name = ?, email = ?, age = ?, " +
                    "course = ?, gpa = ? WHERE student_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, age);
            pstmt.setString(4, course);
            pstmt.setDouble(5, gpa);
            pstmt.setInt(6, studentId);
            
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("✓ Student updated successfully!");
                return true;
            } else {
                System.out.println("✗ Student ID " + studentId + " not found");
            }
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("✗ Email already exists!");
            } else {
                System.out.println("✗ Error updating student: " + e.getMessage());
            }
        } finally {
            closeResources(null, pstmt);
        }
        
        return false;
    }
    
    // ============= DELETE =============
    /**
     * Delete student by ID
     */
    public static boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentId);
            
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                System.out.println("✓ Student deleted successfully!");
                return true;
            } else {
                System.out.println("✗ Student ID " + studentId + " not found");
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error deleting student: " + e.getMessage());
        } finally {
            closeResources(null, pstmt);
        }
        
        return false;
    }
    
    // ============= TRANSACTION (COMMIT/ROLLBACK) =============
    /**
     * Example transaction: Transfer student to another course
     * Demonstrates COMMIT and ROLLBACK
     */
    public static boolean transferStudentCourse(int studentId, String newCourse) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            
            // Start transaction - disable auto-commit
            conn.setAutoCommit(false);
            
            // Check if student exists
            String checkSql = "SELECT student_id FROM students WHERE student_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                conn.rollback();
                System.out.println("✗ Student ID " + studentId + " not found");
                conn.setAutoCommit(true);
                return false;
            }
            
            // Update course
            String updateSql = "UPDATE students SET course = ? WHERE student_id = ?";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setString(1, newCourse);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
            
            // COMMIT transaction
            conn.commit();
            System.out.println("✓ Course transfer completed successfully!");
            System.out.println("  Student " + studentId + " transferred to " + newCourse);
            
            return true;
            
        } catch (SQLException e) {
            // ROLLBACK on error
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("✗ Transaction rolled back due to error");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("✗ Error during transfer: " + e.getMessage());
            return false;
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeResources(null, pstmt);
        }
    }
    
    // ============= UTILITY METHODS =============
    /**
     * Close database resources to prevent memory leaks
     */
    private static void closeResources(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get count of all students
     */
    public static int getStudentCount() {
        String sql = "SELECT COUNT(*) FROM students";
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error counting students: " + e.getMessage());
        } finally {
            closeResources(rs, stmt);
        }
        
        return 0;
    }
}
