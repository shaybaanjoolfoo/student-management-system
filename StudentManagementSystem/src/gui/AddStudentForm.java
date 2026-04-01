package gui;

import database.CourseDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Course;
import model.Student;

public class AddStudentForm extends BaseFrame {

    private JTextField txtId, txtName;
    private JComboBox<String> cmbCourse;
    private JButton btnAdd, btnClear;

    public AddStudentForm() {
        super("Add Student", 500, 320);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Add New Student");
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = createMainPanel(new GridLayout(4, 2, 12, 12));

        txtId = createTextField();
        txtName = createTextField();

        cmbCourse = new JComboBox<>();
        ThemeManager.styleComboBox(cmbCourse);

        btnAdd = createButton("Add Student");
        btnClear = createButton("Clear");

        formPanel.add(createLabel("Student ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("Student Name:"));
        formPanel.add(txtName);
        formPanel.add(createLabel("Course:"));
        formPanel.add(cmbCourse);
        formPanel.add(btnClear);
        formPanel.add(btnAdd);

        add(formPanel, BorderLayout.CENTER);

        loadCourses();

        btnAdd.addActionListener(e -> addStudent());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void loadCourses() {
        cmbCourse.removeAllItems();

        List<Course> courses = new CourseDAO().getAllCourses();
        for (Course course : courses) {
            cmbCourse.addItem(course.getCourseId() + " - " + course.getCourseName());
        }
    }

    private void addStudent() {
        String idText = txtId.getText().trim();
        String name = txtName.getText().trim();
        String selectedCourse = (String) cmbCourse.getSelectedItem();

        if (ValidationUtil.isEmpty(idText) || ValidationUtil.isEmpty(name) || selectedCourse == null) {
            showWarningMessage("Please fill in all fields.");
            return;
        }

        if (!ValidationUtil.isInteger(idText)) {
            showErrorMessage("Student ID must be a valid integer.");
            return;
        }

        if (!ValidationUtil.isValidName(name)) {
            showErrorMessage("Student name must contain only letters and spaces.");
            return;
        }

        if (!ValidationUtil.isWithinLength(name, 50)) {
            showErrorMessage("Student name is too long.");
            return;
        }

        int id = Integer.parseInt(idText);
        int courseId = Integer.parseInt(selectedCourse.split(" - ")[0]);

        Student student = new Student(id, name, courseId);
        StudentDAO studentDAO = new StudentDAO();

        boolean success = studentDAO.addStudent(student);

        if (success) {
            showSuccessMessage("Student added successfully.");
            clearFields();
        } else {
            showErrorMessage("Failed to add student. ID may already exist or course may be invalid.");
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        if (cmbCourse.getItemCount() > 0) {
            cmbCourse.setSelectedIndex(0);
        }
    }
}