package gui;

import database.CourseDAO;
import database.GradeDAO;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Course;
import model.Grade;

public class ViewGradesForm extends BaseFrame implements Refreshable {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;
    private Map<Integer, String> courseMap;

    public ViewGradesForm() {
        super("View Grades", 750, 420);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("View Grades");
        add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Grade ID", "Student ID", "Course", "Grade"});

        table = new JTable(tableModel);
        ThemeManager.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        btnRefresh = createButton("Refresh");
        bottomPanel.add(btnRefresh);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshData());

        refreshData();

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

        GradeDAO dao = new GradeDAO();
        List<Grade> list = dao.getAllGrades();

        for (Grade grade : list) {
            String courseName = courseMap.getOrDefault(grade.getCourseId(), "Unknown");

            tableModel.addRow(new Object[]{
                    grade.getGradeId(),
                    grade.getStudentId(),
                    courseName,
                    grade.getGradeValue()
            });
        }
    }
}