package gui;

import java.awt.Color;
import java.awt.Font;

public class UIConstants {

    public static Color PRIMARY_COLOR = new Color(41, 128, 185);
    public static Color SECONDARY_COLOR = new Color(52, 73, 94);
    public static Color BACKGROUND_COLOR = new Color(236, 240, 241);
    public static Color PANEL_COLOR = new Color(255, 255, 255);
    public static Color BUTTON_TEXT_COLOR = Color.WHITE;
    public static Color LABEL_COLOR = new Color(44, 62, 80);

    public static Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    public static Font LABEL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    public static Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);

    private UIConstants() {
    }

    public static void applyLightTheme() {
        PRIMARY_COLOR = new Color(41, 128, 185);
        SECONDARY_COLOR = new Color(52, 73, 94);
        BACKGROUND_COLOR = new Color(236, 240, 241);
        PANEL_COLOR = new Color(255, 255, 255);
        BUTTON_TEXT_COLOR = Color.WHITE;
        LABEL_COLOR = new Color(44, 62, 80);
    }

    public static void applyDarkTheme() {
        PRIMARY_COLOR = new Color(52, 152, 219);
        SECONDARY_COLOR = new Color(25, 25, 25);
        BACKGROUND_COLOR = new Color(34, 40, 49);
        PANEL_COLOR = new Color(57, 62, 70);
        BUTTON_TEXT_COLOR = Color.WHITE;
        LABEL_COLOR = new Color(238, 238, 238);
    }

    public static void applyColorfulMode() {
        PRIMARY_COLOR = new Color(142, 68, 173);
        SECONDARY_COLOR = new Color(22, 160, 133);
        BACKGROUND_COLOR = new Color(245, 246, 250);
        PANEL_COLOR = new Color(255, 255, 255);
        BUTTON_TEXT_COLOR = Color.WHITE;
        LABEL_COLOR = new Color(44, 62, 80);
    }

    public static void setFontSize(int size) {
        TITLE_FONT = new Font("Arial", Font.BOLD, size + 6);
        LABEL_FONT = new Font("Arial", Font.PLAIN, size);
        BUTTON_FONT = new Font("Arial", Font.BOLD, size);
        FIELD_FONT = new Font("Arial", Font.PLAIN, size);
    }
}