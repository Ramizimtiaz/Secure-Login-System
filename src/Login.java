import com.google.common.hash.Hashing;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;
import java.nio.charset.StandardCharsets;


public class Login {
    private UI2 ui2;

    public Login(UI2 ui2) {
        this.ui2 = ui2;
    }

    public UserRecord getUserRecord(String username) {
        String url = "jdbc:mysql://localhost:3306/pass";
        String user = "root"; // put your own username for sqlserver
        String pass = "";    // put your own password for the sqlserver
        UserRecord record = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, user, pass);

            // SQL query to retrieve data
            String sql = "SELECT `Pass`, `Salt` FROM `username` WHERE `Username` = ?";

            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                byte[] passBytes = rs.getBytes("Pass");
                String passString = new String(passBytes, StandardCharsets.UTF_8);
                // This will print the entire Pass column value
                record = new UserRecord(passBytes);
            }

            // Close the resources
            rs.close();
            preparedStatement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    class UserRecord { //Class for the username
        public byte[] hashedPassword;
        public String salt;

        public UserRecord(byte[] combinedData) {
            // Convert combinedData to a String
            String combinedString = new String(combinedData, StandardCharsets.UTF_8);

            // Assuming your salt is always 16 characters and the hashed password is 64 characters:
            this.salt = combinedString.substring(0, 16);
            this.hashedPassword = combinedString.substring(16).getBytes();
            System.out.println(salt);
            System.out.println(hashedPassword);



        }
    }

    public void passwordcheck(String password, UserRecord record) { //Checks the password with the hashed password
        final String hashed = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        System.out.println(record.hashedPassword);
        System.out.println(new String(record.hashedPassword, StandardCharsets.UTF_8));
        String combinedHash = record.salt.concat(hashed);
        if (!Arrays.equals(record.hashedPassword, combinedHash.substring(16).getBytes(StandardCharsets.UTF_8))) {
            System.out.println("false");
            ui2.Incorrect.setVisible(true);

        } else {
            System.out.println("true");
            ui2.ui4.setVisible(true);
            ui2.ui3.setVisible(false);
            ui2.Incorrect.setText("");

        }
    }


}
