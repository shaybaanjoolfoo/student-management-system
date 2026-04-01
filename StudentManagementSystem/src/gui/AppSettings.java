package gui;

public class AppSettings {

    private static String theme = "Light";
    private static boolean notificationsEnabled = false;
    private static boolean colorfulModeEnabled = false;
    private static int fontSize = 14;

    private AppSettings() {
    }

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String theme) {
        AppSettings.theme = theme;
    }

    public static boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public static void setNotificationsEnabled(boolean notificationsEnabled) {
        AppSettings.notificationsEnabled = notificationsEnabled;
    }

    public static boolean isColorfulModeEnabled() {
        return colorfulModeEnabled;
    }

    public static void setColorfulModeEnabled(boolean colorfulModeEnabled) {
        AppSettings.colorfulModeEnabled = colorfulModeEnabled;
    }

    public static int getFontSize() {
        return fontSize;
    }

    public static void setFontSize(int fontSize) {
        AppSettings.fontSize = fontSize;
    }

    public static void applySettings() {
        if (colorfulModeEnabled) {
            UIConstants.applyColorfulMode();
        } else if ("Dark".equals(theme)) {
            UIConstants.applyDarkTheme();
        } else {
            UIConstants.applyLightTheme();
        }

        UIConstants.setFontSize(fontSize);
    }
}