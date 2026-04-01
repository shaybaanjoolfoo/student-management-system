package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:student.db";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL);

            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");

            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }

    public static void initializeDatabase() {
        String courseTable = "CREATE TABLE IF NOT EXISTS courses ("
                + "course_id INTEGER PRIMARY KEY, "
                + "course_name TEXT NOT NULL UNIQUE CHECK (trim(course_name) <> '')"
                + ");";

        String studentTable = "CREATE TABLE IF NOT EXISTS students ("
                + "id INTEGER PRIMARY KEY, "
                + "name TEXT NOT NULL CHECK (trim(name) <> ''), "
                + "course_id INTEGER NOT NULL, "
                + "FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE RESTRICT ON UPDATE CASCADE"
                + ");";

        String gradeTable = "CREATE TABLE IF NOT EXISTS grades ("
                + "grade_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "student_id INTEGER NOT NULL, "
                + "course_id INTEGER NOT NULL, "
                + "grade_value REAL NOT NULL CHECK (grade_value >= 0 AND grade_value <= 100), "
                + "FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE, "
                + "FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE RESTRICT ON UPDATE CASCADE"
                + ");";

        try (Connection conn = getConnection()) {
            if (conn == null) {
                System.out.println("Database could not be initialized because connection is null.");
                return;
            }

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(courseTable);
                stmt.execute(studentTable);
                stmt.execute(gradeTable);
            }

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}