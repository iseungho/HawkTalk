package Client;

import Client.Login.JDBCconnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditPassword extends JFrame {
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    public ResultSet srs = null;

    private String nickName, sql;
    private StringBuilder sb;

    public EditPassword(String nickName) {
        this.nickName = nickName;
        setTitle("비밀번호 변경");
        setLayout(new GridLayout(7, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JLabel("현재 비밀번호를 입력하세요"));
        JPasswordField nowPwdField = new JPasswordField();
        add(nowPwdField);
        add(new JLabel("변경할 비밀번호를 입력하세요"));
        JPasswordField newPwdField = new JPasswordField();
        add(newPwdField);
        add(new JLabel("비밀번호를 확인해주세요"));
        JPasswordField pwdCheckField = new JPasswordField();
        add(pwdCheckField);
        JButton okBtn = new JButton("변경");
        okBtn.addActionListener(new ActionListener() {
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
                                System.out.println("비밀번호 변경 성공");
                                dispose();
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }
                        } else {
                            System.out.println("비밀번호를 다시 확인해주세요");
                        }
                    } else {
                        System.out.println("현재 비밀번호를 다시 확인해주세요");
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
        add(okBtn);

        setVisible(true);
        setSize(500, 500);
    }
}
