import Client.Login.JDBCconnector;
import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginLayout extends JFrame{
    private JTextField IdTextField;
    private JPasswordField PasswordField;
    private JButton LogInButton;
    private JButton SignUpButton;
    private JPanel LoginPanel;
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    private StringBuilder sb;
    private String sql;
    private ResultSet srs = null;



    public LoginLayout() {

        setContentPane(LoginPanel);
        setSize(400, 600);
        setTitle("홍톡2.0");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        IdTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (IdTextField.getText().equals("아이디를 입력하세요")) {
                    IdTextField.setText("");
                }
            }
        });
        IdTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (IdTextField.getText().equals("아이디를 입력하세요")) {
                    IdTextField.setText("");
                }
            }
        });

        class MyActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String id = IdTextField.getText();
                if (id.equals("아이디를 입력하세요")) {
                    JOptionPane.showMessageDialog(null,"아이디를 입력하세요");
                    return;
                }
                String pwd = PasswordField.getText();
                try {
                    sb = new StringBuilder();
                    sql = sb.append("select * from usertable where ID='").append(id).append("';").toString();
                    srs = stmt.executeQuery(sql);
                    if (srs.next()) {
                        if (srs.getString("Pwd").equals(pwd)) {
                            JOptionPane.showMessageDialog(null,"로그인 성공!");
                            dispose();
                            new ChatLayout();
                        } else {
                            JOptionPane.showMessageDialog(null,"비밀번호가 틀렸습니다");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"존재하지 않는 아이디입니다");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        PasswordField.addActionListener(new MyActionListener());
        LogInButton.addActionListener(new MyActionListener());

        SignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpLayout();
            }
        });
    }

    public static void main(String[] args) {
        new LoginLayout();
    }

}
