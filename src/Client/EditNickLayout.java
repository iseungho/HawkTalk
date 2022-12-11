package Client;

import DB.JDBCconnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditNickLayout extends JFrame{
    private JButton EditNicknameButton;
    private JTextField NickTextField;
    private JLabel Label;
    private JPanel EditNickPanel;
    private String nickName, sql;
    private StringBuilder sb;
    private ClientBack clientBack;
    private ProfileLayout profileLayout;

    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    public ResultSet srs = null;

    public void setClientBack(ClientBack clientBack) {
        this.clientBack = clientBack;
    }

    public void setProfileLayout(ProfileLayout profileLayout) {
        this.profileLayout = profileLayout;
    }

    public EditNickLayout(String nickName){
        this.nickName = nickName;
        setContentPane(EditNickPanel);
        setSize(200,300);
        setTitle("닉네임 변경");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        EditNicknameButton.addActionListener(new ActionListener() {
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
                    sb.append("update usertable set NickName='").append(NickTextField.getText()).append("' WHERE Name='").append(name).append("';");
                    sql = sb.toString();
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,"닉네임 변경 성공");
                    clientBack.resetNickName(NickTextField.getText());
                    JOptionPane.showMessageDialog(null,"재접속 해주세요");
                    System.exit(0);
                } catch (SQLException se) {
                        se.printStackTrace();
                }
            }

        });
    }
    public void setGui(ProfileLayout profileLayout) {
        this.profileLayout = profileLayout;
    }
}
