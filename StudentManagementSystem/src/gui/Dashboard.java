package gui;

import java.awt.*;
import javax.swing.*;

public class Dashboard extends BaseFrame {

    public Dashboard() {
        super("Dashboard", 700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("Student Management System Dashboard");
        add(lblTitle, BorderLayout.NORTH);

        JPanel buttonPanel = createMainPanel(new GridLayout(5, 3, 12, 12));

        buttonPanel.add(createNavButton("Add Student", () -> new AddStudentForm()));
        buttonPanel.add(createNavButton("View Students", () -> new ViewStudentsForm()));
        buttonPanel.add(createNavButton("Search Student", () -> new SearchStudentForm()));

        buttonPanel.add(createNavButton("Student Details", () -> new StudentDetailsForm()));
        buttonPanel.add(createNavButton("Add Course", () -> new AddCourseForm()));
        buttonPanel.add(createNavButton("View Courses", () -> new ViewCoursesForm()));

        buttonPanel.add(createNavButton("Assign Grade", () -> new AssignGradeForm()));
        buttonPanel.add(createNavButton("View Grades", () -> new ViewGradesForm()));
        buttonPanel.add(createNavButton("Reports", () -> new ReportsForm()));

        buttonPanel.add(createNavButton("Filter By Course", () -> new CourseFilterForm()));
        buttonPanel.add(createNavButton("Settings", () -> new SettingsForm()));
        buttonPanel.add(createNavButton("About System", () -> new AboutSystemForm()));

        JButton btnLogout = createButton("Logout");
        btnLogout.addActionListener(e -> logout());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(UIConstants.BACKGROUND_COLOR);
        southPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createNavButton(String text, Runnable action) {
        JButton button = createButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Do you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginPage();
            dispose();
        }
    }
}