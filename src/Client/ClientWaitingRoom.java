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
    JTextField chatField = new JTextField(45);
    JButton sendBtn = new JButton("전송");

    TextArea chatedArea = new TextArea(30, 50);
    TextArea userListArea = new TextArea(30, 15);

    public ClientWaitingRoom(String nickName, String ipAddress, int portNum) {
        this.nickName = nickName;
        setTitle("HawkTalk");
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(750, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatedArea.setEditable(false);
        userListArea.setEditable(false);
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

        clientPanel.add(chatedArea);
        JPanel userPanel = new JPanel(new BorderLayout());
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        userPanel.add(userLabel, BorderLayout.NORTH);
        userPanel.add(userListArea, BorderLayout.SOUTH);
        clientPanel.add(userPanel);
        clientPanel.add(chatField);
        clientPanel.add(sendBtn);
        add(clientPanel);

        clientBack.setGui(this);
        clientBack.setUserInfo(nickName, ipAddress, portNum);
        clientBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.
    }

    public void appendMessage(String Message) {
        chatedArea.append(Message);
    }

    public void resetUserList(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        for (String nickName : nickNameList) {
            userListArea.append(nickName + "\n");
        }
    }
}
