package Client;

import DB.JDBCconnector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WithdrawalLayout extends JFrame{
    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    public ResultSet srs = null;

    private String nickName, sql;
    private StringBuilder sb;

    private JPanel WithdrawalPanel;
    private JButton withdrawalButton;
    private JPasswordField passwordField1;
    public WithdrawalLayout(String nickName){
        this.nickName = nickName;
        setContentPane(WithdrawalPanel);

        setSize(200,300);
        setTitle("회원 탈퇴");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        withdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sb = new StringBuilder();
                    sb.append("select * from usertable where NickName='").append(nickName).append("';");
                    sql = sb.toString();
                    srs = stmt.executeQuery(sql);
                    srs.next();
                    if (srs.getString("Pwd").equals(passwordField1.getText())) {
                        sb = new StringBuilder();
                        Integer yesorno = null;
                        yesorno = JOptionPane.showConfirmDialog(null, "정말 회원 탈퇴 하시겠습니까?", "회원 탈퇴", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if(yesorno == 0){
                            sb.append("delete from usertable WHERE NickName='").append(nickName).append("';");
                            sql = sb.toString();
                            stmt.executeUpdate(sql);
                            JOptionPane.showMessageDialog(null, "회원 탈퇴 성공");
                            System.exit(0);
                        }
                        else{
                            dispose();
                        }
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
    }
}
