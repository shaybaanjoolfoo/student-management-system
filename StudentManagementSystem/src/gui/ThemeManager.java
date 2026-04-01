package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThemeManager {

    private ThemeManager() {
    }

    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(UIConstants.BACKGROUND_COLOR);
    }

    public static JPanel createMainPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(UIConstants.PANEL_COLOR);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        return panel;
    }

    public static void styleTitle(JLabel label) {
        label.setFont(UIConstants.TITLE_FONT);
        label.setForeground(UIConstants.LABEL_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void styleLabel(JLabel label) {
        label.setFont(UIConstants.LABEL_FONT);
        label.setForeground(UIConstants.LABEL_COLOR);
    }

    public static void styleTextField(JTextField textField) {
        textField.setFont(UIConstants.FIELD_FONT);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
    }

    public static void stylePasswordField(JPasswordField passwordField) {
        passwordField.setFont(UIConstants.FIELD_FONT);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
    }

    public static void styleButton(JButton button) {
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(UIConstants.PRIMARY_COLOR);
        button.setForeground(UIConstants.BUTTON_TEXT_COLOR);
        button.setFocusPainted(false);
    }

    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(UIConstants.FIELD_FONT);
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
    }

    public static void styleTextArea(JTextArea textArea) {
        textArea.setFont(UIConstants.FIELD_FONT);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
    }

    public static void styleTable(JTable table) {
        table.setFont(UIConstants.FIELD_FONT);
        table.setRowHeight(24);
        table.getTableHeader().setFont(UIConstants.BUTTON_FONT);
        table.getTableHeader().setBackground(UIConstants.SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(UIConstants.PRIMARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
    }

    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}