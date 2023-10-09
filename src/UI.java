
import javax.swing.*;    // login for the password/username maker
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public JLabel usernamemessageLabel = new JLabel("");
    public JLabel passnamemessageLabel = new JLabel("");
    public JFrame f;  // original frame
    private JFrame log; // login
    public JPasswordField password;
    private UI2 ui2;


    public JTextField username;
    private ActionListener input;
    public UI(){
        SwingUtilities.invokeLater(() -> {

             f = new JFrame(); // Creating an instance of JFrame
            f.setSize(400, 500); // Setting the frame's size (400 width and 500 height)
            f.setLayout(null); // Using no layout managerJ


            password = new JPasswordField();
            password.setBounds(100,150,200,30);
            f.add(password);


            username = new JTextField();
            username.setBorder(new LineBorder(Color.RED)); // Set the border color to red
            username.setBounds(100, 100, 200, 30);// Set the bounds (x, y, width, height)

            JLabel viewpassword = new JLabel("Password : ");
            viewpassword.setBounds(100,150,200,30);
            viewpassword.setVisible(false);
            f.add(viewpassword);



            usernamemessageLabel.setBounds(30, 250, 400, 30); // Message label update based off username
            f.add(usernamemessageLabel);

            passnamemessageLabel.setBounds(30, 280, 300, 30); // Message label update based off password
            f.add(passnamemessageLabel);


            f.add(username);// Add the text field to the frame
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
            f.setVisible(true);

            JLabel Title = new JLabel("Welcome to password storer,");
            Title.setBounds(120, 50, 450, 30);
            f.add(Title);

            JLabel Title2 = new JLabel("please input password below.");
            Title2.setBounds(120, 65, 400, 30);
            f.add(Title2);

            JLabel Title3 = new JLabel( "Username");
            Title3.setBounds(30, 100, 100, 30);
            f.add(Title3);

            JLabel Title4 = new JLabel( "Password");
            Title4.setBounds(31, 150, 100, 30);
            f.add(Title4);

            JButton hide = new JButton("Hide Password");
            hide.setBounds(50,200,150,30);
            f.add(hide);
            hide.setVisible(false);

            JButton view = new JButton("View Password");
            view.setBounds(50,200,150,30);
            f.add(view);
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

            JButton skip = new JButton("Skip to login");
            skip.setBounds(200,300,200,30);
            f.add(skip);
            skip.setVisible(false);
            skip.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.setVisible(false);
                    skip.setVisible(true);
                    ui2.ui3.setVisible(true);


                }
            });


            JButton submit = new JButton("Submit");
            submit.setBounds(210, 200, 100, 30);
            submit.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                   if(input != null){
                       input.actionPerformed(e);

                   }

                }
            });
            f.add(submit);



            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
            f.setVisible(true);

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

    public void updateMessage(String message) {
        usernamemessageLabel.setText(message);
    }
    public void updateMessagepass(String message){
        passnamemessageLabel.setText(message);
    }
    public void setBorder(){
        usernamemessageLabel.setBounds(10, 250, 400, 30);
    } // all code above for the first UI screen
    public void setUI2(UI2 ui2) {
        this.ui2 = ui2;
    }
}


