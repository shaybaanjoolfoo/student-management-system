package gui;

public class ValidationUtil {

    private ValidationUtil() {
        // Prevent object creation
    }

    // Check if a text value is empty
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Check if string is an integer
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if string is a double
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if grade is between 0 and 100
    public static boolean isValidGrade(String value) {
        try {
            double grade = Double.parseDouble(value.trim());
            return grade >= 0 && grade <= 100;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if text contains only letters and spaces
    public static boolean isValidName(String value) {
        return value != null && value.trim().matches("[a-zA-Z ]+");
    }

    // Check if text length is within limit
    public static boolean isWithinLength(String value, int maxLength) {
        return value != null && value.trim().length() <= maxLength;
    }
}