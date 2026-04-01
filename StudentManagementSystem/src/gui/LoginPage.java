package gui;

import database.DBConnection;
import java.awt.*;
import javax.swing.*;

public class LoginPage extends BaseFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnClear;

    public LoginPage() {
        super("Login", 420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DBConnection.initializeDatabase();

        JLabel lblTitle = createTitleLabel("Student Management System Login");
        add(lblTitle, BorderLayout.NORTH);

        JPanel mainPanel = createMainPanel(new GridLayout(3, 2, 12, 12));

        txtUsername = createTextField();
        txtPassword = createPasswordField();
        btnLogin = createButton("Login");
        btnClear = createButton("Clear");

        mainPanel.add(createLabel("Username:"));
        mainPanel.add(txtUsername);
        mainPanel.add(createLabel("Password:"));
        mainPanel.add(txtPassword);
        mainPanel.add(btnClear);
        mainPanel.add(btnLogin);

        add(mainPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> login());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)) {
            showWarningMessage("Please enter username and password.");
            return;
        }

        if (username.equals("admin") && password.equals("admin")) {
            showSuccessMessage("Login successful!");
            new Dashboard();
            dispose();
        } else {
            showErrorMessage("Invalid username or password.");
        }
    }

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }
}