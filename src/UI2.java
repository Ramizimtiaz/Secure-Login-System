import javax.swing.*;    // UI for the login screen
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI2 {
    public JFrame ui3;
    private UI ui;
    public JFrame ui4;
    private UI uiReference;
    private JTextField pass;
    private JTextField username;
    private ActionListener input;
    public JPasswordField password;
    public JLabel Incorrect;

    public UI2(UI ui) {
        this.uiReference = ui;

        SwingUtilities.invokeLater(() -> {
            ui3 = new JFrame();
            ui3.setSize(400, 500);
            ui3.setLayout(null); // Use null layout
            ui3.setVisible(false);

            ui4 = new JFrame();
            ui4.setSize(400, 500);
            ui4.setLayout(null);
            ui4.setVisible(false);

            Incorrect = new JLabel("Password or Username is incorrect ");
            Incorrect.setBounds(100,300,250,30);
            Incorrect.setVisible(false);
            ui3.add(Incorrect);

            JLabel viewpassword = new JLabel("Password : ");
            viewpassword.setBounds(80,170,200,30);
            viewpassword.setVisible(false);
            ui3.add(viewpassword);

            JButton hide = new JButton("Hide Password");
            hide.setBounds(40,210,150,30);
            ui3.add(hide);
            hide.setVisible(false);

            JLabel Title  = new JLabel("<html>Password Stored Sucessfully<br>Now use the same username and password to login</html>");
            Title.setBounds(15, 50, 400, 50);  // Adjust these values as needed
            ui3.add(Title);

            password = new JPasswordField();
            password.setBounds(80,170,200,30);
            ui3.add(password);

            username = new JTextField();
            username.setBorder(new LineBorder(Color.RED)); // Set the border color to red
            username.setBounds(80, 120, 200, 30);// Set the bounds (x, y, width, height)
            ui3.add(username);

            JLabel Title3 = new JLabel( "Username");
            Title3.setBounds(20, 120, 100, 30);
            ui3.add(Title3);

            JLabel Title4 = new JLabel( "Password");
            Title4.setBounds(20, 170, 100, 30);
            ui3.add(Title4);

            JButton submit = new JButton("Submit");
            submit.setBounds(200, 210, 100, 30);
            submit.addActionListener(e -> {
                if(input != null) {
                    input.actionPerformed(e);
                }
            });
            ui3.add(submit);
            JButton view = new JButton("View Password");
            view.setBounds(40,210,150,30);
            ui3.add(view);
            view.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    String PasswordText = new String(password.getPassword());
                    viewpassword.setVisible(true);
                    password.setVisible(false);
                    viewpassword.setText(PasswordText);
                    view.setVisible(false);
                    hide.setVisible(true);


                }
            });
            hide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewpassword.setVisible(false);
                    password.setVisible(true);
                    hide.setVisible(false);
                    view.setVisible(true);
                }
            });

            // Components for the LAST UI screen:
            JLabel Final = new JLabel("<html>Congratulations you have made a password and login <br>Please check out my other projects!</html>");
            Final.setBounds(0, 100, 400, 30);  // Adjust the width here to fit the entire message
            JButton back = new JButton("Return");
            back.setBounds(130, 220, 100, 30);
            ui4.add(back);
            ui4.add(Final);
            back.addActionListener(e -> {
                ui4.setVisible(false);
                uiReference.f.setVisible(true);
                uiReference.password.setText("");
                uiReference.username.setText("");
                uiReference.passnamemessageLabel.setText("");
                uiReference.usernamemessageLabel.setText("");


            });

            ui3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        });
    }

    public void addSubmitListener(ActionListener listener) {
        input = listener;
    }

    public String userInputp() {
        if (password != null) {
            return password.getText();
        } else {
            return ""; // Return an empty string as a fallback if the text field is null
        }
    }

    public String userInputu() {
        if (username != null) {
            return username.getText();
        } else {
            return ""; // Return an empty string as a fallback if the text field is null
        }
    }
    public void setUI(UI ui) {
        this.ui = ui;
    }
}
