package gui;

import database.CourseDAO;
import database.GradeDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Course;
import model.Grade;

public class AssignGradeForm extends BaseFrame {

    private JTextField txtId, txtGrade;
    private JComboBox<String> cmbCourse;
    private JButton btnAdd, btnClear;

    public AssignGradeForm() {
        super("Assign Grade", 450, 280);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Assign Grade");
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = createMainPanel(new GridLayout(4, 2, 12, 12));

        txtId = createTextField();
        txtGrade = createTextField();

        cmbCourse = new JComboBox<>();
        ThemeManager.styleComboBox(cmbCourse);

        btnAdd = createButton("Assign Grade");
        btnClear = createButton("Clear");

        formPanel.add(createLabel("Student ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("Course:"));
        formPanel.add(cmbCourse);
        formPanel.add(createLabel("Grade:"));
        formPanel.add(txtGrade);
        formPanel.add(btnClear);
        formPanel.add(btnAdd);

        add(formPanel, BorderLayout.CENTER);

        loadCourses();

        btnAdd.addActionListener(e -> assignGrade());
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

    private void assignGrade() {
        String idText = txtId.getText().trim();
        String gradeText = txtGrade.getText().trim();
        String selectedCourse = (String) cmbCourse.getSelectedItem();

        if (ValidationUtil.isEmpty(idText) || ValidationUtil.isEmpty(gradeText) || selectedCourse == null) {
            showWarningMessage("Please fill in all fields.");
            return;
        }

        if (!ValidationUtil.isInteger(idText)) {
            showErrorMessage("Student ID must be an integer.");
            return;
        }

        if (!ValidationUtil.isValidGrade(gradeText)) {
            showErrorMessage("Grade must be a number between 0 and 100.");
            return;
        }

        int studentId = Integer.parseInt(idText);
        int courseId = Integer.parseInt(selectedCourse.split(" - ")[0]);
        double gradeValue = Double.parseDouble(gradeText);

        Grade grade = new Grade(0, studentId, courseId, gradeValue);
        GradeDAO dao = new GradeDAO();

        if (dao.addGrade(grade)) {
            showSuccessMessage("Grade assigned successfully.");
            clearFields();
        } else {
            showErrorMessage("Failed to assign grade. Check that the student exists and belongs to the selected course.");
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtGrade.setText("");
        if (cmbCourse.getItemCount() > 0) {
            cmbCourse.setSelectedIndex(0);
        }
    }
}