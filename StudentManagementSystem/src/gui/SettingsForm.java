package gui;

import java.awt.*;
import javax.swing.*;

public class SettingsForm extends BaseFrame {

    private JRadioButton rdoLight, rdoDark;
    private JCheckBox chkNotifications, chkColorfulMode;
    private JSlider sliderFontSize;
    private JButton btnSave;

    public SettingsForm() {
        super("Settings", 600, 400);
        setLayout(new BorderLayout());

        JLabel lblTitle = createTitleLabel("System Settings");
        add(lblTitle, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel appearancePanel = createMainPanel(new GridLayout(5, 1, 10, 10));
        JPanel optionsPanel = createMainPanel(new GridLayout(2, 1, 10, 10));

        rdoLight = new JRadioButton("Light Theme");
        rdoDark = new JRadioButton("Dark Theme");

        rdoLight.setBackground(UIConstants.PANEL_COLOR);
        rdoDark.setBackground(UIConstants.PANEL_COLOR);
        rdoLight.setForeground(UIConstants.LABEL_COLOR);
        rdoDark.setForeground(UIConstants.LABEL_COLOR);

        ButtonGroup group = new ButtonGroup();
        group.add(rdoLight);
        group.add(rdoDark);

        chkNotifications = new JCheckBox("Enable notifications");
        chkColorfulMode = new JCheckBox("Enable colorful mode");

        chkNotifications.setBackground(UIConstants.PANEL_COLOR);
        chkColorfulMode.setBackground(UIConstants.PANEL_COLOR);
        chkNotifications.setForeground(UIConstants.LABEL_COLOR);
        chkColorfulMode.setForeground(UIConstants.LABEL_COLOR);

        sliderFontSize = new JSlider(12, 24, AppSettings.getFontSize());
        sliderFontSize.setMajorTickSpacing(2);
        sliderFontSize.setPaintTicks(true);
        sliderFontSize.setPaintLabels(true);
        sliderFontSize.setBackground(UIConstants.PANEL_COLOR);
        sliderFontSize.setForeground(UIConstants.LABEL_COLOR);

        btnSave = createButton("Save Settings");

        if ("Dark".equals(AppSettings.getTheme())) {
            rdoDark.setSelected(true);
        } else {
            rdoLight.setSelected(true);
        }

        chkNotifications.setSelected(AppSettings.isNotificationsEnabled());
        chkColorfulMode.setSelected(AppSettings.isColorfulModeEnabled());

        appearancePanel.add(createLabel("Choose Theme:"));
        appearancePanel.add(rdoLight);
        appearancePanel.add(rdoDark);
        appearancePanel.add(createLabel("Font Size:"));
        appearancePanel.add(sliderFontSize);

        optionsPanel.add(chkNotifications);
        optionsPanel.add(chkColorfulMode);

        tabbedPane.addTab("Appearance", appearancePanel);
        tabbedPane.addTab("Options", optionsPanel);

        add(tabbedPane, BorderLayout.CENTER);
        add(btnSave, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveSettings());

        setVisible(true);
    }

private void saveSettings() {
    if (rdoDark.isSelected()) {
        AppSettings.setTheme("Dark");
    } else {
        AppSettings.setTheme("Light");
    }

    AppSettings.setNotificationsEnabled(chkNotifications.isSelected());
    AppSettings.setColorfulModeEnabled(chkColorfulMode.isSelected());
    AppSettings.setFontSize(sliderFontSize.getValue());
    AppSettings.applySettings();

    String message = "Settings saved successfully.";

    if (chkNotifications.isSelected()) {
        message += "\nNotifications: Enabled";
    } else {
        message += "\nNotifications: Disabled";
    }

    if (chkColorfulMode.isSelected()) {
        message += "\nColorful mode: Enabled";
    } else {
        message += "\nColorful mode: Disabled";
    }

    message += "\nTheme: " + AppSettings.getTheme();
    message += "\nFont Size: " + AppSettings.getFontSize();
    message += "\n\nOpen a new window to see visual theme changes.";

    showSuccessMessage(message);
}    
}