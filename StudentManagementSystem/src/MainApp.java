import gui.AppSettings;
import gui.LoginPage;

public class MainApp {
    public static void main(String[] args) {
        AppSettings.applySettings();
        new LoginPage();
    }
}