package gui;

import database.CourseDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Course;
import model.Student;

public class CourseFilterForm extends BaseFrame {

    private JComboBox<String> cmbCourses;
    private JButton btnShow;
    private JTextArea area;

    public CourseFilterForm() {
        super("Filter By Course", 550, 400);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Filter Students By Course");
        add(lblTitle, BorderLayout.NORTH);

        JPanel topPanel = createMainPanel(new FlowLayout());

        cmbCourses = new JComboBox<>();
        ThemeManager.styleComboBox(cmbCourses);

        btnShow = createButton("Show Students");

        topPanel.add(createLabel("Select Course:"));
        topPanel.add(cmbCourses);
        topPanel.add(btnShow);

        area = new JTextArea();
        area.setEditable(false);
        ThemeManager.styleTextArea(area);

        add(topPanel, BorderLayout.SOUTH);
        add(new JScrollPane(area), BorderLayout.CENTER);

        loadCourses();

        btnShow.addActionListener(e -> filterByCourse());

        setVisible(true);
    }

    private void loadCourses() {
        cmbCourses.removeAllItems();

        List<Course> courses = new CourseDAO().getAllCourses();
        for (Course course : courses) {
            cmbCourses.addItem(course.getCourseId() + " - " + course.getCourseName());
        }
    }

    private void filterByCourse() {
        String selected = (String) cmbCourses.getSelectedItem();

        if (selected == null) {
            showWarningMessage("No course selected.");
            return;
        }

        int courseId = Integer.parseInt(selected.split(" - ")[0]);
        String courseName = selected.split(" - ", 2)[1];

        List<Student> students = new StudentDAO().getStudentsByCourseId(courseId);

        area.setText("");
        area.append("STUDENTS IN COURSE\n");
        area.append("========================================\n");
        area.append("Course: " + courseName + "\n\n");

        if (students.isEmpty()) {
            area.append("No students found for this course.\n");
            return;
        }

        for (Student student : students) {
            area.append("ID: " + student.getId()
                    + " | Name: " + student.getName() + "\n");
        }
    }
}