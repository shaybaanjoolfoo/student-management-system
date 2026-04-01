package gui;

import database.CourseDAO;
import database.StudentDAO;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Course;
import model.Student;

public class ViewStudentsForm extends BaseFrame implements Refreshable {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnEdit, btnDelete, btnRefresh, btnDetails;
    private Map<Integer, String> courseMap;

    public ViewStudentsForm() {
        super("View Students", 850, 450);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("View Students");
        add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Course"});

        table = new JTable(tableModel);
        ThemeManager.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        btnEdit = createButton("Edit");
        btnDelete = createButton("Delete");
        btnRefresh = createButton("Refresh");
        btnDetails = createButton("Details");

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnDetails);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshData();

        btnRefresh.addActionListener(e -> refreshData());
        btnDelete.addActionListener(e -> deleteStudent());
        btnEdit.addActionListener(e -> editStudent());
        btnDetails.addActionListener(e -> showStudentDetails());

        setVisible(true);
    }

    private void loadCourseMap() {
        courseMap = new HashMap<>();
        List<Course> courses = new CourseDAO().getAllCourses();

        for (Course course : courses) {
            courseMap.put(course.getCourseId(), course.getCourseName());
        }
    }

    @Override
    public void refreshData() {
        tableModel.setRowCount(0);
        loadCourseMap();

        StudentDAO studentDAO = new StudentDAO();
        List<Student> students = studentDAO.getAllStudents();

        for (Student student : students) {
            String courseName = courseMap.getOrDefault(student.getCourseId(), "Unknown");

            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    courseName
            });
        }
    }

    private int getSelectedStudentId() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            showWarningMessage("Please select a student first.");
            return -1;
        }

        return Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
    }

    private void deleteStudent() {
        int id = getSelectedStudentId();
        if (id == -1) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            StudentDAO studentDAO = new StudentDAO();
            boolean success = studentDAO.deleteStudent(id);

            if (success) {
                showSuccessMessage("Student deleted successfully.");
                refreshData();
            } else {
                showErrorMessage("Failed to delete student.");
            }
        }
    }

    private void editStudent() {
        int id = getSelectedStudentId();
        if (id == -1) {
            return;
        }

        new EditStudentForm(id, this);
    }

    private void showStudentDetails() {
        int id = getSelectedStudentId();
        if (id == -1) {
            return;
        }

        Student student = new StudentDAO().getStudentById(id);

        if (student == null) {
            showErrorMessage("Student not found.");
            return;
        }

        String courseName = courseMap.getOrDefault(student.getCourseId(), "Unknown");

        JOptionPane.showMessageDialog(
                this,
                "ID: " + student.getId()
                        + "\nName: " + student.getName()
                        + "\nCourse: " + courseName,
                "Student Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}