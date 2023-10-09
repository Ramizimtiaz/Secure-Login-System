import com.google.common.hash.Hashing;
import java.util.Random;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Algorithm {
    private UI ui;
    private UI2 ui2;

    public Algorithm(UI ui, UI2 ui2) {
        this.ui = ui;
        this.ui2 = ui2;
    }

    public boolean length(String password) {
        if (password.length() < 12 && !commonCheck(password)) {
            ui.updateMessagepass("Password too short");
            return false;
        } else if (commonCheck(password)) {
            ui.updateMessagepass("Password contains a common password");
            return false;
        } else {
            hash(password);
            ui.updateMessagepass("Password looks good");
            return true;
        }
    }

    public String hash(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    public boolean commonCheck(String password) {
        String[] repeat = {"abc", "qwerty", "password", "password123", "qwerty123"};
        for (String commonPassword : repeat) {
            if (password.toLowerCase().contains(commonPassword)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkusername(String username) {
        if (username.length() < 8 && username.matches(".*[ ,;+=-_!@()~`<>?/|{}].*")) {
            ui.updateMessage("<html>Username has to be longer than 8 characters<br>and contains invalid characters.</html>");
            ui.setBorder();
            return false;
        } else if (username.length() < 8) {
            ui.updateMessage("Username has to be longer than 8 characters.");
            return false;
        } else if (username.matches(".*[ ,;].*")) {
            ui.updateMessage("Username contains invalid characters.");
            return false;
        } else {
            ui.updateMessage("Username looks good");
            return true;
        }
    }

    public String salt() {
        String list = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+~";
        StringBuilder saltBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 16; i++) {
            int randomIndex = random.nextInt(list.length());
            char randomChar = list.charAt(randomIndex);
            saltBuilder.append(randomChar);
        }

        System.out.println(saltBuilder);
        return saltBuilder.toString();
    }

    public void passwordstore(String username, String password) {
        if (!commonCheck(username) && length(password)) {
            storeToDatabase(username, password);
        }
    }

    private void storeToDatabase(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/pass";
        String user = "root";
        String pass = "";

        String salt = salt();
        String hashedPassword = hash(password);
        hashedPassword = salt + hashedPassword;
        byte[] newPassword = hashedPassword.getBytes(StandardCharsets.UTF_8);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);

            String sql = "INSERT INTO `username` (`Username`, `Pass` , `Salt`) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setBytes(2, newPassword);
            preparedStatement.setString(3, salt);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
                ui.f.setVisible(false);
                ui2.ui3.setVisible(true);
            } else {
                System.out.println("Data insertion failed.");
            }

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
