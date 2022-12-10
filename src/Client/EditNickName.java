package Client;

import Client.Login.JDBCconnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditNickName extends JFrame {
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    public ResultSet srs = null;

    JTextField nicknameField;

    private String nickName, sql;
    private StringBuilder sb;

    private ProfileRoom pfr;

    private ClientBack clientBack;

    public void setClientBack(ClientBack clientBack) {
        this.clientBack = clientBack;
    }

    public EditNickName(String nickName) {
        this.nickName = nickName;
        nicknameField = new JTextField(nickName);
        setTitle("닉네임 변경");
        setLayout(new GridLayout(7, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JLabel("변경할 닉네임을 입력하세요"));
        add(nicknameField);
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
                    String name = srs.getString("Name");
                    sb = new StringBuilder();
                    sb.append("update usertable set NickName='").append(nicknameField.getText())
                            .append("' WHERE Name='").append(name).append("';");
                    sql = sb.toString();
                    stmt.executeUpdate(sql);
                    System.out.println("닉네임 변경 성공");
                    clientBack.resetNickName(nicknameField.getText());
                    clientBack.sendMessage("!EditUserNickName" + nickName + ":" + nicknameField.getText());
                    dispose();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }

        });
        add(okBtn);


        setVisible(true);
        setSize(500, 500);
    }

    public void setGui(ProfileRoom pfr) {
        this.pfr = pfr;
    }
}
