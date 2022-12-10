package Client;

import Client.Login.JDBCconnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class ProfileRoom extends JFrame {
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;

    private String nickName, newNickName;
    private String cmd;

    private ClientBack clientBack;

    public void setClientBack(ClientBack clientBack) {
        this.clientBack = clientBack;
    }

    public ProfileRoom(String nickName) {
        this.nickName = nickName;
        cmd = "!EditUserNickName" + nickName + ":" + nickName;
        setTitle("Edit Profile");
        setVisible(true);
        setLayout(new GridLayout(3, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton editNickNameBtn = new JButton("Edit NickName");
        add(editNickNameBtn);
        editNickNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditNickName enn = new EditNickName(nickName);
                enn.setClientBack(clientBack);
            }
        });
        JButton editPwdBtn = new JButton("Edit Password");
        add(editPwdBtn);
        editPwdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditPassword(clientBack.getNickName());
            }
        });
        JButton withdrawalBtn = new JButton("Withdrawal HawkTalk");
        add(withdrawalBtn);
        withdrawalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Withdrawal(clientBack.getNickName());
            }
        });
        setSize(500, 500);
    }

    public void setCmd(String str) {
        cmd = str;
    }

    public String getCmd() {
        return cmd;
    }
}
