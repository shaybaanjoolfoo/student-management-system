package gui;

import database.CourseDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import model.Course;
import model.Student;

public class SearchStudentForm extends BaseFrame {

    private JTextField txtName;
    private JButton btnSearch, btnClear;
    private JTextArea area;
    private Map<Integer, String> courseMap;

    public SearchStudentForm() {
        super("Search Student", 550, 400);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Search Student");
        add(lblTitle, BorderLayout.NORTH);

        JPanel topPanel = createMainPanel(new GridLayout(2, 2, 10, 10));

        txtName = createTextField();
        btnSearch = createButton("Search");
        btnClear = createButton("Clear");

        topPanel.add(createLabel("Enter Student Name:"));
        topPanel.add(txtName);
        topPanel.add(btnSearch);
        topPanel.add(btnClear);

        area = new JTextArea();
        area.setEditable(false);
        ThemeManager.styleTextArea(area);

        add(topPanel, BorderLayout.SOUTH);
        add(new JScrollPane(area), BorderLayout.CENTER);

        loadCourseMap();

        btnSearch.addActionListener(e -> searchStudent());
        btnClear.addActionListener(e -> area.setText(""));

        setVisible(true);
    }

    private void loadCourseMap() {
        courseMap = new HashMap<>();
        List<Course> courses = new CourseDAO().getAllCourses();

        for (Course course : courses) {
            courseMap.put(course.getCourseId(), course.getCourseName());
        }
    }

    private void searchStudent() {
        String searchName = txtName.getText().trim();

        if (ValidationUtil.isEmpty(searchName)) {
            showWarningMessage("Please enter a student name.");
            return;
        }

        List<Student> students = new StudentDAO().getAllStudents();

        area.setText("");
        boolean found = false;

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchName.toLowerCase())) {
                String courseName = courseMap.getOrDefault(student.getCourseId(), "Unknown");

                area.append("ID: " + student.getId() + "\n");
                area.append("Name: " + student.getName() + "\n");
                area.append("Course: " + courseName + "\n");
                area.append("-----------------------------\n");

                found = true;
            }
        }

        if (!found) {
            area.setText("No student found.");
        }
    }
}