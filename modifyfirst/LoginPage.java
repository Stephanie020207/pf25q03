package modifyfirst;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPage extends JDialog {
    private boolean loginSuccessful = false;

    public LoginPage() {
        setTitle("Login");
        setModal(true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateCredentials(username, password)) {
                loginSuccessful = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        pack();
        setLocationRelativeTo(null);
    }

    private boolean validateCredentials(String username, String password) {
        // MySQL connection details
        String host = "mysql-3fdac62e-stephanie020207-eabc.d.aivencloud.com";
        String port = "22555";
        String databaseName = "tictactoe";
        String userName = "avnadmin";
        String dbPassword = "AVNS_QtTtu790duzMg5o1a1Z";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT password FROM users WHERE username = '" + username + "'")) {

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return password.equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false; // Invalid credentials
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);

        if (loginPage.isLoginSuccessful()) {
            System.out.println("Login successful! Proceeding to game...");
            // Replace with your next step, e.g., opening the game or board selection
        } else {
            System.out.println("Login failed. Exiting...");
        }
    }
}