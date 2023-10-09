import com.google.common.hash.Hashing;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            setupUI();
        });
    }

    private static void setupUI() {
        // Create and link UI instances
        UI ui = new UI();
        UI2 ui2 = new UI2(ui);
        ui.setUI2(ui2);
        ui2.setUI(ui);

        setupUIListeners(ui, ui2);
    }

    private static void setupUIListeners(UI ui, UI2 ui2) {
        // Set up action listener for UI's submit button
        ui.addSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUISubmit(ui, ui2);
            }
        });

        // Set up action listener for UI2's submit button
        ui2.addSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUI2Submit(ui2);
            }
        });
    }

    private static void handleUISubmit(UI ui, UI2 ui2) {
        String password = ui.userInputp();
        String username = ui.userInputu();

        Algorithm algorithm = new Algorithm(ui, ui2);
        algorithm.checkusername(username);
        algorithm.length(password);
        algorithm.passwordstore(username, password);
    }

    private static void handleUI2Submit(UI2 ui2) {
        Login login = new Login(ui2);
        String username = ui2.userInputu();
        String password = ui2.userInputp();

        Login.UserRecord record = login.getUserRecord(username);
        login.passwordcheck(password, record);
    }
}
