package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Course;

public class CourseDAO {

    // Add course
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_id, course_name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
            return false;
        }
    }

    // Get all courses
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name")
                );
                courseList.add(course);
            }

        } catch (Exception e) {
            System.out.println("Error fetching courses: " + e.getMessage());
        }

        return courseList;
    }

    // Get course by id
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name")
                );
            }

        } catch (Exception e) {
            System.out.println("Error getting course by id: " + e.getMessage());
        }

        return null;
    }

    // Get course id by name
    public int getCourseIdByName(String courseName) {
        String sql = "SELECT course_id FROM courses WHERE course_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("course_id");
            }

        } catch (Exception e) {
            System.out.println("Error getting course id by name: " + e.getMessage());
        }

        return -1;
    }
}