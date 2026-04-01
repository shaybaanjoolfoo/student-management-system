package gui;

import database.CourseDAO;
import database.GradeDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import model.Course;
import model.Grade;
import model.Student;

public class ReportsForm extends BaseFrame {

    private JTextArea area;
    private JButton btnAll, btnTop, btnAvg, btnFilter, btnClear;
    private JComboBox<String> cmbCourse;
    private Map<Integer, String> courseMap;
    private Map<Integer, String> studentMap;

    public ReportsForm() {
        super("Reports", 750, 520);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Student Reports");
        add(lblTitle, BorderLayout.NORTH);

        area = new JTextArea();
        area.setEditable(false);
        ThemeManager.styleTextArea(area);

        JScrollPane scrollPane = new JScrollPane(area);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = createMainPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAll = createButton("All Students");
        btnTop = createButton("Top Students");
        btnAvg = createButton("Average Grade");
        btnFilter = createButton("Filter by Course");
        btnClear = createButton("Clear");

        cmbCourse = new JComboBox<>();
        ThemeManager.styleComboBox(cmbCourse);

        controlPanel.add(btnAll);
        controlPanel.add(btnTop);
        controlPanel.add(btnAvg);
        controlPanel.add(createLabel("Course:"));
        controlPanel.add(cmbCourse);
        controlPanel.add(btnFilter);
        controlPanel.add(btnClear);

        add(controlPanel, BorderLayout.SOUTH);

        loadCourses();
        loadStudents();

        btnAll.addActionListener(e -> showAllStudents());
        btnTop.addActionListener(e -> showTopStudents());
        btnAvg.addActionListener(e -> showAverageGrade());
        btnFilter.addActionListener(e -> filterByCourse());
        btnClear.addActionListener(e -> clearReport());

        setVisible(true);
    }

    private void loadCourses() {
        cmbCourse.removeAllItems();
        courseMap = new HashMap<>();

        List<Course> courses = new CourseDAO().getAllCourses();
        for (Course course : courses) {
            courseMap.put(course.getCourseId(), course.getCourseName());
            cmbCourse.addItem(course.getCourseId() + " - " + course.getCourseName());
        }
    }

    private void loadStudents() {
        studentMap = new HashMap<>();

        List<Student> students = new StudentDAO().getAllStudents();
        for (Student student : students) {
            studentMap.put(student.getId(), student.getName());
        }
    }

    private String getCourseName(int courseId) {
        return courseMap.getOrDefault(courseId, "Unknown");
    }

    private String getStudentName(int studentId) {
        return studentMap.getOrDefault(studentId, "Unknown");
    }

    private void showAllStudents() {
        area.setText("");
        area.append("ALL STUDENTS REPORT\n");
        area.append("========================================\n\n");

        List<Student> list = new StudentDAO().getAllStudents();

        if (list.isEmpty()) {
            area.append("No students found.\n");
            return;
        }

        for (Student student : list) {
            area.append("ID: " + student.getId() + "\n");
            area.append("Name: " + student.getName() + "\n");
            area.append("Course: " + getCourseName(student.getCourseId()) + "\n");
            area.append("----------------------------------------\n");
        }
    }

    private void showTopStudents() {
        area.setText("");
        area.append("TOP STUDENTS REPORT (Grade >= 80)\n");
        area.append("========================================\n\n");

        List<Grade> grades = new GradeDAO().getAllGrades();
        boolean found = false;

        for (Grade grade : grades) {
            if (grade.getGradeValue() >= 80) {
                area.append("Student ID: " + grade.getStudentId() + "\n");
                area.append("Name: " + getStudentName(grade.getStudentId()) + "\n");
                area.append("Course: " + getCourseName(grade.getCourseId()) + "\n");
                area.append("Grade: " + grade.getGradeValue() + "\n");
                area.append("----------------------------------------\n");
                found = true;
            }
        }

        if (!found) {
            area.append("No top students found.\n");
        }
    }

    private void showAverageGrade() {
        area.setText("");
        List<Grade> grades = new GradeDAO().getAllGrades();

        area.append("AVERAGE GRADE REPORT\n");
        area.append("========================================\n\n");

        if (grades.isEmpty()) {
            area.append("No grades available.\n");
            return;
        }

        double total = 0;
        for (Grade grade : grades) {
            total += grade.getGradeValue();
        }

        double average = total / grades.size();
        area.append("Average Grade of all assigned grades: " + String.format("%.2f", average) + "\n");
    }

    private void filterByCourse() {
        String selectedCourse = (String) cmbCourse.getSelectedItem();

        if (selectedCourse == null) {
            showWarningMessage("Please select a course.");
            return;
        }

        int courseId = Integer.parseInt(selectedCourse.split(" - ")[0]);
        String courseName = selectedCourse.split(" - ", 2)[1];

        area.setText("");
        area.append("FILTERED REPORT BY COURSE\n");
        area.append("========================================\n");
        area.append("Course: " + courseName + "\n\n");

        List<Grade> grades = new GradeDAO().getAllGrades();
        boolean found = false;

        for (Grade grade : grades) {
            if (grade.getCourseId() == courseId) {
                area.append("Student ID: " + grade.getStudentId() + "\n");
                area.append("Name: " + getStudentName(grade.getStudentId()) + "\n");
                area.append("Grade: " + grade.getGradeValue() + "\n");
                area.append("----------------------------------------\n");
                found = true;
            }
        }

        if (!found) {
            area.append("No grades found for this course.\n");
        }
    }

    private void clearReport() {
        area.setText("");
        if (cmbCourse.getItemCount() > 0) {
            cmbCourse.setSelectedIndex(0);
        }
    }
}