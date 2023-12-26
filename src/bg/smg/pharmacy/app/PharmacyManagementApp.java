package bg.smg.pharmacy.app;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PharmacyManagementApp {

    private static Map<String, String> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginWindow();
        });
    }

    public static Map<String, String> getUserDatabase() {
        return userDatabase;
    }
}
