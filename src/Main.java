import com.google.common.hash.Hashing;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // Create an instance of the UI class
            UI ui = new UI();
            UI2 ui2 = new UI2(ui);

            // Link them together
            ui.setUI2(ui2);
            ui2.setUI(ui); // Pass ui instance to UI2

            // Add listener for the UI class's submit button
            ui.addSubmitListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String password = ui.userInputp(); // Retrieves the Password
                    String username = ui.userInputu(); // Retrieves the username

                    Algorithm algorithm = new Algorithm(ui, ui2);
                    algorithm.checkusername(username); //Checks username
                    algorithm.length(password); //Checks the password
                    algorithm.passwordstore(username, password); // Store the password and username
                }
            });

            // Add listener for the UI2 class's submit button
            ui2.addSubmitListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Login login = new Login(ui2); //UI for login
                    String username = ui2.userInputu(); //Retrieves the username
                    String password = ui2.userInputp(); // Retrieves the password
                    Login.UserRecord record = login.getUserRecord(username); // Checks the username with the database
                    login.passwordcheck(password, record); // Checks password associated with the username
                }
            });
        });
    }
}
