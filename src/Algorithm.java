import com.google.common.hash.Hashing;
import java.util.Random;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Algorithm {
    private UI ui; // Create Password UI
    private UI2 ui2; // Login UI
    public Algorithm(UI ui, UI2 ui2) {
        this.ui = ui;
        this.ui2 = ui2;
    }




    public boolean length(String password) { //Checks the password

        if (password.length() < 12 && !commonCheck(password)){
            
            ui.updateMessagepass("Password too short");
            return false;
            } else if (commonCheck(password)) {
            ui.updateMessagepass("Password contains a common password");
            return false;
        }
          else {
              hash(password);
            ui.updateMessagepass("Password looks good");
              return true;
        }
    }


        public String hash (String password){ //Hashes the password using SHA256
            final String hashed = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();

            return hashed;
        }


        public boolean commonCheck (String password) { //Checks for common password

            String[] repeat = {"abc", "qwerty", "password", "password123", "qwerty123"};
            for (String commonPassword : repeat) {
                if (password.toLowerCase().contains(commonPassword)) {
                    return true;
                }
            }
            return false;
        }

        public boolean checkusername(String username){ //checks the username
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

        public String salt(){ //Makes a salt to append to the password
            String list = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()_+~";
            Random random = new Random();

            StringBuilder saltBuilder = new StringBuilder();

            for (int i = 0; i < 16; i++) {
                int randomIndex = random.nextInt(list.length());
                char randomChar = list.charAt(randomIndex);
                saltBuilder.append(randomChar);
            }
            System.out.println(saltBuilder);
            return saltBuilder.toString();

        }

        public void passwordstore(String username, String password){ //If all requirments met stores password in a sql server
            // Database connection parameters
            if(!commonCheck(username) && length(password)){

                String url = "jdbc:mysql://localhost:3306/pass";
                String user = "root"; // put your own mysql server to test
                String pass = ""; // put the password of your sql server

                // Data to be inserted
                String newUsername = username;
                String samplePassword = hash(password);
                String salt = salt();
                samplePassword = salt + samplePassword;
                byte[] newPassword =  samplePassword.getBytes(StandardCharsets.UTF_8);

                try {
                    // Load the MySQL JDBC driver
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Establish a connection to the database
                    Connection connection = DriverManager.getConnection(url, user, pass);

                    // SQL query to insert data, assuming your Pass column is of type varbinary(32)
                    String sql = "INSERT INTO `username` (`Username`, `Pass` , `Salt`) VALUES (?, ?, ?)";

                    // Create a PreparedStatement to execute the query
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, newUsername);
                    preparedStatement.setBytes(2, newPassword);
                    preparedStatement.setString(3, salt);

                    // Execute the query
                    int rowsInserted = preparedStatement.executeUpdate();

                    if (rowsInserted > 0) {
                        System.out.println("Data inserted successfully.");
                        ui.f.setVisible(false);// Sets orginal  frame to invisible
                        ui2.ui3.setVisible(true);// Sets new  frame to visible
                    } else {
                        System.out.println("Data insertion failed.");
                    }

                    // Close the resources
                    preparedStatement.close();
                    connection.close();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
    }




}
