package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Student;

public class StudentDAO {

    // Check if course exists
    private boolean courseExists(int courseId) {
        String sql = "SELECT course_id FROM courses WHERE course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error checking course existence: " + e.getMessage());
            return false;
        }
    }

    // Add student
    public boolean addStudent(Student student) {
        if (!courseExists(student.getCourseId())) {
            System.out.println("Course does not exist.");
            return false;
        }

        String sql = "INSERT INTO students (id, name, course_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getCourseId());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // Get all students
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("course_id")
                );
                studentList.add(student);
            }

        } catch (Exception e) {
            System.out.println("Error fetching students: " + e.getMessage());
        }

        return studentList;
    }

    // Get one student by id
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("course_id")
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting student by id: " + e.getMessage());
        }

        return null;
    }

    // Update student
    public boolean updateStudent(Student student) {
        if (!courseExists(student.getCourseId())) {
            System.out.println("Course does not exist.");
            return false;
        }

        String sql = "UPDATE students SET name = ?, course_id = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getCourseId());
            pstmt.setInt(3, student.getId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // Delete student
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    // Get students by course id
    public List<Student> getStudentsByCourseId(int courseId) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("course_id")
                );
                studentList.add(student);
            }

        } catch (Exception e) {
            System.out.println("Error getting students by course id: " + e.getMessage());
        }

        return studentList;
    }

    // Get students by course name using join
    public List<Student> getStudentsByCourseName(String courseName) {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT s.* FROM students s "
                   + "JOIN courses c ON s.course_id = c.course_id "
                   + "WHERE c.course_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("course_id")
                );
                studentList.add(student);
            }

        } catch (Exception e) {
            System.out.println("Error getting students by course name: " + e.getMessage());
        }

        return studentList;
    }

    // Get course name by student id
    public String getCourseNameByStudent(int studentId) {
        String sql = "SELECT c.course_name FROM students s "
                   + "JOIN courses c ON s.course_id = c.course_id "
                   + "WHERE s.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("course_name");
            }

        } catch (Exception e) {
            System.out.println("Error getting course name by student: " + e.getMessage());
        }

        return "";
    }
}