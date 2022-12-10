package Client;

import DB.JDBCconnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SignUpLayout extends JFrame{
    private JTextField NameTextField;
    private JTextField ClassnumTextField;
    private JTextField NickNameTextField;
    private JButton SignupButton;
    private JPanel SignUpPanel;
    private JPasswordField PasswordFieldOk;
    private JPasswordField PasswordField;
    private JTextField IDTextField;
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    private ResultSet srs = jc.srs;

    SignUpLayout(){
        setContentPane(SignUpPanel);
        setSize(350, 550);
        setTitle("회원가입");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        SignupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                if (NameTextField.getText().equals("")) {
                    flag = false;
                    JOptionPane.showMessageDialog(null,"이름을 입력하세요");
                } else if (ClassnumTextField.getText().equals("")) {
                    flag = false;
                    JOptionPane.showMessageDialog(null,"학번을 입력하세요");
                } else if (NickNameTextField.getText().equals("")) {
                    flag = false;
                    JOptionPane.showMessageDialog(null,"닉네임을 입력하세요");
                } else if (IDTextField.getText().equals("")) {
                    flag = false;
                    JOptionPane.showMessageDialog(null,"아이디를 입력하세요");
                } else if (PasswordField.getText().equals("")) {
                    flag = false;
                    JOptionPane.showMessageDialog(null,"비밀번호를 입력하세요");
                }
                else if (!PasswordField.getText().equals(PasswordFieldOk.getText())){
                    flag = false;
                    JOptionPane.showMessageDialog(null,"비밀번호를 확인해주세요");
                }
                if (!flag) return;
                ArrayList<String> userData = new ArrayList<>();
                userData.add(NameTextField.getText());
                userData.add(ClassnumTextField.getText());
                userData.add(NickNameTextField.getText());
                userData.add(IDTextField.getText());
                userData.add(PasswordField.getText());
                try {
                    int a = jc.isValid(stmt, userData);
                    // System.out.println(a);
                    switch (a) {
                        case 1 -> {
                            JOptionPane.showMessageDialog(null,"이미 가입되어 있는 사용자입니다");
                            flag = false;
                        }
                        case 2 -> {
                            JOptionPane.showMessageDialog(null,"이미 사용중인 닉네임입니다");
                            flag = false;
                        }
                        case 3 -> {
                            JOptionPane.showMessageDialog(null,"이미 사용중인 아이디입니다");
                            flag = false;
                        }
                        default -> flag = true;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (!flag) return;
                JOptionPane.showMessageDialog(null,"회원가입 성공!");
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("insert into usertable (Name, ClassNumber, NickName, ID, Pwd) values ('").append(userData.get(0))
                            .append("', '").append(userData.get(1)).append("', '").append(userData.get(2)).append("', '")
                            .append(userData.get(3)).append("', '").append(userData.get(4)).append("');");
                    String sql = sb.toString();
                    stmt.executeUpdate(sql); // 레코드 추가
                    jc.printTable(stmt);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
    }
}
