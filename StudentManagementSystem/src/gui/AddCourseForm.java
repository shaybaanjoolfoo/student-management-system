package gui;

import database.CourseDAO;
import java.awt.*;
import javax.swing.*;
import model.Course;

public class AddCourseForm extends BaseFrame {

    private JTextField txtId, txtName;
    private JButton btnAdd, btnClear;

    public AddCourseForm() {
        super("Add Course", 450, 250);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Add Course");
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = createMainPanel(new GridLayout(3, 2, 12, 12));

        txtId = createTextField();
        txtName = createTextField();

        btnAdd = createButton("Add Course");
        btnClear = createButton("Clear");

        formPanel.add(createLabel("Course ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("Course Name:"));
        formPanel.add(txtName);
        formPanel.add(btnClear);
        formPanel.add(btnAdd);

        add(formPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addCourse());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void addCourse() {
        String idText = txtId.getText().trim();
        String name = txtName.getText().trim();

        if (ValidationUtil.isEmpty(idText) || ValidationUtil.isEmpty(name)) {
            showWarningMessage("Please fill in all fields.");
            return;
        }

        if (!ValidationUtil.isInteger(idText)) {
            showErrorMessage("Course ID must be an integer.");
            return;
        }

        if (!ValidationUtil.isWithinLength(name, 50)) {
            showErrorMessage("Course name is too long.");
            return;
        }

        int id = Integer.parseInt(idText);

        Course course = new Course(id, name);
        CourseDAO dao = new CourseDAO();

        if (dao.addCourse(course)) {
            showSuccessMessage("Course added successfully.");
            clearFields();
        } else {
            showErrorMessage("Failed to add course. ID may already exist.");
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
    }
}