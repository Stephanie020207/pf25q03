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
        setUndecorated(true); // Remove window decorations
        setModal(true);

        // Set full-screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLayout(new BorderLayout());

        // Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(34, 40, 49)); // Dark background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel);

        // Login Form Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(400, 200));

        JLabel usernameLabel = createStyledLabel("Username:");
        formPanel.add(usernameLabel);
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        formPanel.add(usernameField);

        JLabel passwordLabel = createStyledLabel("Password:");
        formPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
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
        formPanel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());
        formPanel.add(cancelButton);

        backgroundPanel.add(formPanel);
        setLocationRelativeTo(null);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, JLabel.RIGHT);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(52, 73, 94));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
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
}