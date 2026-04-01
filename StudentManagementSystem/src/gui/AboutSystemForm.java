package gui;

import java.awt.*;
import javax.swing.*;

public class AboutSystemForm extends BaseFrame {

    public AboutSystemForm() {
        super("About System", 500, 300);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("About Student Management System");

        JTextArea area = new JTextArea();
        area.setEditable(false);
        ThemeManager.styleTextArea(area);

        area.setText(
                "Student Management System\n\n"
                + "This application was developed using Java Swing, SQLite, and JDBC.\n\n"
                + "Main Features:\n"
                + "- Login system\n"
                + "- Student CRUD\n"
                + "- Course management\n"
                + "- Grade management\n"
                + "- Reports\n"
                + "- Search and filtering\n"
                + "- Advanced Swing components\n\n"
        );

        add(lblTitle, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);

        setVisible(true);
    }
}