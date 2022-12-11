package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileLayout extends JFrame{
    private JButton ChangeNickButton;
    private JButton ChangePassButton;
    private String nickName, newNickName;
    private String cmd;


    private JButton WithdrawalButton;
    private JPanel ProfilePanel;
    private ClientBack clientBack;
    public void setClientBack(ClientBack clientBack) {
        this.clientBack = clientBack;
    }

    public ProfileLayout(String nickName){
        this.nickName = nickName;
        cmd = "!EditUserNickName" + nickName + ":" + nickName;
        setContentPane(ProfilePanel);
        setSize(250,350);
        setTitle("프로필 수정");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ChangeNickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditNickLayout editNickLayout = new EditNickLayout(clientBack.getNickName());
                editNickLayout.setClientBack(clientBack);
            }
        });
        ChangePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditpwLayout(clientBack.getNickName());
            }
        });
        WithdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawalLayout(clientBack.getNickName());
            }
        });
    }
    public void setCmd(String str) {
        cmd = str;
    }

    public String getCmd() {
        return cmd;
    }
}
