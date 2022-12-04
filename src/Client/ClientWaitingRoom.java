package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ClientWaitingRoom extends JFrame {
    //클라이언트용 채팅창
    String nickName;
    ClientBack clientBack = new ClientBack();
    JPanel clientPanel = new JPanel();
    JLabel userLabel = new JLabel("유저 목록");
    JLabel User = new JLabel(nickName);
    JTextField chatField = new JTextField(45);
    JButton sendBtn = new JButton("전송");

    TextArea chatedList = new TextArea(30, 50);
    TextArea userList = new TextArea(30, 15);

    public ClientWaitingRoom(String nickName, String ipAddress, int portNum) {
        this.nickName = nickName;
        setTitle("HawkTalk");
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(750, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatedList.setEditable(false);
        userList.setEditable(false);
        chatField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                // 키보드 엔터키를 누르고, 입력값이 1이상일때만 전송되도록 합니다.
                String Message = chatField.getText().trim();
                if (e.getKeyCode() == KeyEvent.VK_ENTER && Message.length() > 0) {
                    clientBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    chatField.setText(null);
                }
            }
        });
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 전송 버튼을 누르고, 입력값이 1이상일때만 전송되도록 합니다.
                String Message = chatField.getText().trim();
                if (e.getSource() == sendBtn && Message.length() > 0) {
                    clientBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    chatField.setText(null);
                }
            }
        });

        clientPanel.add(User);
        clientPanel.add(chatedList);
        clientPanel.add(userLabel);
        clientPanel.add(userList);
        clientPanel.add(chatField);
        clientPanel.add(sendBtn);
        add(clientPanel);

        clientBack.setGui(this);
        clientBack.setUserInfo(nickName, ipAddress, portNum);
        clientBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.
    }

    public void appendMessage(String Message) {
        chatedList.append(Message);
    }

    public void resetUserList(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        userList.setText(null);
        for (String nickName : nickNameList) {
            userList.append(nickName + "\n");
        }
    }
}
