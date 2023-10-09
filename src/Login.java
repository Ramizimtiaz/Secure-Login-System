import com.google.common.hash.Hashing;
import java.sql.*;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

public class Login {
    private UI2 ui2;

    public Login(UI2 ui2) {
        this.ui2 = ui2;
    }

    // Retrieves the UserRecord for a given username
    public UserRecord getUserRecord(String username) {
        String url = "jdbc:mysql://localhost:3306/pass";
        String user = "root";  // put username
        String pass = ""; // put password
        UserRecord record = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT `Pass`, `Salt` FROM `username` WHERE `Username` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                byte[] passBytes = rs.getBytes("Pass");
                record = new UserRecord(passBytes);
            }

            rs.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return record;
    }

    // Compares the input password to the stored hashed password
    public void passwordcheck(String password, UserRecord record) {
        String hashed = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        String combinedHash = record.salt.concat(hashed);

        if (!Arrays.equals(record.hashedPassword, combinedHash.substring(16).getBytes(StandardCharsets.UTF_8))) {
            ui2.Incorrect.setVisible(true);
            System.out.println("false");
        } else {
            ui2.ui4.setVisible(true);
            ui2.ui3.setVisible(false);
            ui2.Incorrect.setText("");
            System.out.println("true");
        }
    }

    // Internal class to store the user's salt and hashed password
    class UserRecord {
        public byte[] hashedPassword;
        public String salt;

        public UserRecord(byte[] combinedData) {
            String combinedString = new String(combinedData, StandardCharsets.UTF_8);

            this.salt = combinedString.substring(0, 16);
            this.hashedPassword = combinedString.substring(16).getBytes();
            System.out.println(salt);
            System.out.println(new String(hashedPassword, StandardCharsets.UTF_8));
        }
    }
}
