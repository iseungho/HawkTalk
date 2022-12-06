import javax.swing.*;

public class SignUpLayout extends JFrame{
    private JTextField 아이디TextField;
    private JTextField 학번TextField;
    private JTextField 이름TextField;
    private JButton SignupButton;
    private JPanel SignUpPanel;
    private JPasswordField 비밀번호PasswordField;
    private JPasswordField 비밀번호PasswordField1;
    private JButton 처음으로돌아가기Button;
    private JTextField IDTextField;

    SignUpLayout(){
        setContentPane(SignUpPanel);
        setSize(400, 600);
        setTitle("회원가입");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new SignUpLayout();
    }
}
