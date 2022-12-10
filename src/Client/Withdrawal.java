package Client;

import Client.Login.JDBCconnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Withdrawal extends JFrame {
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    public ResultSet srs = null;

    private String nickName, sql;
    private StringBuilder sb;

    public Withdrawal(String nickName) {
        this.nickName = nickName;
        setTitle("회원 탈퇴");
        setLayout(new GridLayout(3, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JLabel("회원 탈퇴를 위해 비밀번호를 입력해주세요"));
        JTextField testField = new JTextField();
        JPasswordField PwdField = new JPasswordField();
        add(PwdField);
        JButton okBtn = new JButton("탈퇴");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sb = new StringBuilder();
                    sb.append("select * from usertable where NickName='").append(nickName).append("';");
                    sql = sb.toString();
                    srs = stmt.executeQuery(sql);
                    srs.next();
                    if (srs.getString("Pwd").equals(PwdField.getText())) {
                        sb = new StringBuilder();
                        sb.append("delete from usertable WHERE NickName='").append(nickName).append("';");
                        sql = sb.toString();
                        stmt.executeUpdate(sql);
                        System.out.println("회원 탈퇴 성공");
                        System.exit(0);
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
