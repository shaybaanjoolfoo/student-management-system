package gui;

import database.CourseDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Course;
import model.Student;

public class EditStudentForm extends BaseFrame {

    private JTextField txtId, txtName;
    private JComboBox<String> cmbCourse;
    private JButton btnSave, btnClear;
    private int studentId;
    private Refreshable parentForm;

    public EditStudentForm(int studentId, Refreshable parentForm) {
        super("Edit Student", 500, 320);
        this.studentId = studentId;
        this.parentForm = parentForm;

        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Edit Student");
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = createMainPanel(new GridLayout(4, 2, 12, 12));

        txtId = createTextField();
        txtName = createTextField();

        cmbCourse = new JComboBox<>();
        ThemeManager.styleComboBox(cmbCourse);

        txtId.setEditable(false);

        btnSave = createButton("Save Changes");
        btnClear = createButton("Reset");

        formPanel.add(createLabel("Student ID:"));
        formPanel.add(txtId);
        formPanel.add(createLabel("Student Name:"));
        formPanel.add(txtName);
        formPanel.add(createLabel("Course:"));
        formPanel.add(cmbCourse);
        formPanel.add(btnClear);
        formPanel.add(btnSave);

        add(formPanel, BorderLayout.CENTER);

        loadCourses();
        loadStudentData();

        btnSave.addActionListener(e -> updateStudent());
        btnClear.addActionListener(e -> loadStudentData());

        setVisible(true);
    }

    private void loadCourses() {
        cmbCourse.removeAllItems();

        List<Course> courses = new CourseDAO().getAllCourses();
        for (Course course : courses) {
            cmbCourse.addItem(course.getCourseId() + " - " + course.getCourseName());
        }
    }

    private void loadStudentData() {
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getStudentById(studentId);

        if (student != null) {
            txtId.setText(String.valueOf(student.getId()));
            txtName.setText(student.getName());

            for (int i = 0; i < cmbCourse.getItemCount(); i++) {
                String item = cmbCourse.getItemAt(i);
                int courseId = Integer.parseInt(item.split(" - ")[0]);

                if (courseId == student.getCourseId()) {
                    cmbCourse.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            showErrorMessage("Student not found.");
            dispose();
        }
    }

    private void updateStudent() {
        String idText = txtId.getText().trim();
        String name = txtName.getText().trim();
        String selectedCourse = (String) cmbCourse.getSelectedItem();

        if (ValidationUtil.isEmpty(name) || selectedCourse == null) {
            showWarningMessage("Please fill in all fields.");
            return;
        }

        if (!ValidationUtil.isValidName(name)) {
            showErrorMessage("Student name must contain only letters and spaces.");
            return;
        }

        int id = Integer.parseInt(idText);
        int courseId = Integer.parseInt(selectedCourse.split(" - ")[0]);

        Student student = new Student(id, name, courseId);
        StudentDAO studentDAO = new StudentDAO();

        boolean success = studentDAO.updateStudent(student);

        if (success) {
            showSuccessMessage("Student updated successfully.");

            if (parentForm != null) {
                parentForm.refreshData();
            }

            dispose();
        } else {
            showErrorMessage("Failed to update student.");
        }
    }
}