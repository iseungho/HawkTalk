import javax.swing.*;

public class LoginLayout extends JFrame{
    private JTextField IdTextField;
    private JPasswordField passwordField;
    private JButton LogInButton;
    private JButton SignUpButton;
    private JPanel LoginPanel;
    private JRadioButton AutoLoginRadio;

    public LoginLayout(){
        setContentPane(LoginPanel);
        setSize(400, 600);
        setTitle("홍톡2.0");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new LoginLayout();
    }

}
