package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Grade;

public class GradeDAO {

    private boolean studentExists(int studentId) {
        String sql = "SELECT id FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error checking student existence: " + e.getMessage());
            return false;
        }
    }

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

    private boolean studentBelongsToCourse(int studentId, int courseId) {
        String sql = "SELECT id FROM students WHERE id = ? AND course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Error checking student-course relationship: " + e.getMessage());
            return false;
        }
    }

    // Assign grade
    public boolean addGrade(Grade grade) {
        if (!studentExists(grade.getStudentId())) {
            System.out.println("Student does not exist.");
            return false;
        }

        if (!courseExists(grade.getCourseId())) {
            System.out.println("Course does not exist.");
            return false;
        }

        if (!studentBelongsToCourse(grade.getStudentId(), grade.getCourseId())) {
            System.out.println("Student is not registered in this course.");
            return false;
        }

        String sql = "INSERT INTO grades (student_id, course_id, grade_value) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setDouble(3, grade.getGradeValue());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Error adding grade: " + e.getMessage());
            return false;
        }
    }

    // Get all grades
    public List<Grade> getAllGrades() {
        List<Grade> gradeList = new ArrayList<>();
        String sql = "SELECT * FROM grades";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Grade grade = new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDouble("grade_value")
                );
                gradeList.add(grade);
            }

        } catch (Exception e) {
            System.out.println("Error fetching grades: " + e.getMessage());
        }

        return gradeList;
    }

    // Get course name using course id
    public String getCourseNameById(int courseId) {
        String sql = "SELECT course_name FROM courses WHERE course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("course_name");
            }

        } catch (Exception e) {
            System.out.println("Error getting course name: " + e.getMessage());
        }

        return "";
    }
}