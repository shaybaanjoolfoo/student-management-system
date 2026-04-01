package gui;

import database.CourseDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Course;

public class ViewCoursesForm extends BaseFrame implements Refreshable {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;

    public ViewCoursesForm() {
        super("View Courses", 650, 400);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("View Courses");
        add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Course ID", "Course Name"});

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

    @Override
    public void refreshData() {
        tableModel.setRowCount(0);

        CourseDAO dao = new CourseDAO();
        List<Course> list = dao.getAllCourses();

        for (Course course : list) {
            tableModel.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName()
            });
        }
    }
}