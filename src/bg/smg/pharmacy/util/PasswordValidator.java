package bg.smg.pharmacy.util;

public class PasswordValidator {

    public static boolean isPasswordValid(String password) {
        // Password requirements: 8 or more characters, at least one uppercase letter, one digit, and one special symbol
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }
}
