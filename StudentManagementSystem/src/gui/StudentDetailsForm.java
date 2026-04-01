package gui;

import database.CourseDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Course;
import model.Student;

public class StudentDetailsForm extends BaseFrame {

    private JTextField txtId;
    private JButton btnLoad;
    private JTextArea area;

    public StudentDetailsForm() {
        super("Student Details", 500, 350);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Student Details");
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = createMainPanel(new GridLayout(1, 3, 10, 10));
        txtId = createTextField();
        btnLoad = createButton("Load Details");

        panel.add(createLabel("Student ID:"));
        panel.add(txtId);
        panel.add(btnLoad);

        area = new JTextArea();
        area.setEditable(false);
        ThemeManager.styleTextArea(area);

        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(area), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadStudent());

        setVisible(true);
    }

    private String getCourseNameById(int courseId) {
        List<Course> courses = new CourseDAO().getAllCourses();

        for (Course course : courses) {
            if (course.getCourseId() == courseId) {
                return course.getCourseName();
            }
        }

        return "Unknown";
    }

    private void loadStudent() {
        String idText = txtId.getText().trim();

        if (!ValidationUtil.isInteger(idText)) {
            showErrorMessage("Please enter a valid student ID.");
            return;
        }

        int id = Integer.parseInt(idText);
        Student student = new StudentDAO().getStudentById(id);

        if (student == null) {
            area.setText("Student not found.");
            return;
        }

        String courseName = getCourseNameById(student.getCourseId());

        area.setText("");
        area.append("Student Information\n");
        area.append("====================\n");
        area.append("ID: " + student.getId() + "\n");
        area.append("Name: " + student.getName() + "\n");
        area.append("Course ID: " + student.getCourseId() + "\n");
        area.append("Course Name: " + courseName + "\n");
    }
}