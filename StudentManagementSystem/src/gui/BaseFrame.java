package gui;

import java.awt.*;
import javax.swing.*;

public abstract class BaseFrame extends JFrame {

    public BaseFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ThemeManager.styleFrame(this);
    }

    protected JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        ThemeManager.styleTitle(label);
        return label;
    }

    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        ThemeManager.styleLabel(label);
        return label;
    }

    protected JTextField createTextField() {
        JTextField textField = new JTextField();
        ThemeManager.styleTextField(textField);
        return textField;
    }

    protected JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        ThemeManager.stylePasswordField(passwordField);
        return passwordField;
    }

    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        ThemeManager.styleButton(button);
        return button;
    }

    protected JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        ThemeManager.styleComboBox(comboBox);
        return comboBox;
    }

    protected JPanel createMainPanel(LayoutManager layout) {
        return ThemeManager.createMainPanel(layout);
    }

    protected void showSuccessMessage(String message) {
        ThemeManager.showSuccess(this, message);
    }

    protected void showErrorMessage(String message) {
        ThemeManager.showError(this, message);
    }

    protected void showWarningMessage(String message) {
        ThemeManager.showWarning(this, message);
    }
}