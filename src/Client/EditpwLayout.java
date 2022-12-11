package Client;

import DB.JDBCconnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditpwLayout extends JFrame{
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    public ResultSet srs = null;

    private String nickName, sql;
    private StringBuilder sb;
    private JButton EditpwButton;
    private JTextField nowPwdField;
    private JPasswordField newPwdField;
    private JPasswordField pwdCheckField;
    private JPanel EditpwPanel;

    public EditpwLayout(String nickName){
        this.nickName = nickName;

        setContentPane(EditpwPanel);
        setSize(200,300);
        setTitle("비밀번호 변경");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        EditpwButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sb = new StringBuilder();
                    sb.append("select * from usertable where NickName='").append(nickName).append("';");
                    sql = sb.toString();
                    srs = stmt.executeQuery(sql);
                    srs.next();
                    if (srs.getString("Pwd").equals(nowPwdField.getText())) {
                        if (pwdCheckField.getText().equals(newPwdField.getText())) {
                            try {
                                sb = new StringBuilder();
                                sb.append("update usertable set Pwd='").append(newPwdField.getText())
                                        .append("' WHERE NickName='").append(nickName).append("';");
                                sql = sb.toString();
                                stmt.executeUpdate(sql);
                                JOptionPane.showMessageDialog(null,"비밀번호 변경 성공");
                                dispose();
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,"비밀번호를 확인해 주세요");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"현재 비밀번호를 확인해 주세요");

                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
    }
}
