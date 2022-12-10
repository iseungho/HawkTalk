package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChattingRoom extends JFrame {
    String nickName, ipAddress;
    int portNum;
    ChattingRoomBack chattingRoomBack = new ChattingRoomBack();
    JPanel clientPanel = new JPanel();

    JLabel userLabel = new JLabel("유저 목록");
    JTextField chatField = new JTextField(45);

    JButton sendBtn = new JButton("전송");

    TextArea chatedArea = new TextArea(30, 50);
    TextArea userListArea = new TextArea(20, 15);

    public ChattingRoom(String nickName, String roomName, String ipAddress, int portNum) {
        this.nickName = nickName;
        this.portNum = portNum;
        setTitle(roomName);
        setVisible(true);
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        chatedArea.setEditable(false);
        userListArea.setEditable(false);

        chatField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                // 키보드 엔터키를 누르고, 입력값이 1이상일때만 전송되도록 합니다.
                String Message = chatField.getText().trim();
                if (e.getKeyCode() == KeyEvent.VK_ENTER && Message.length() > 0) {
                    chattingRoomBack.sendMessage("[" + nickName + "]: " + Message + "\n");
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
                    chattingRoomBack.sendMessage("[" + nickName + "]: " + Message + "\n");
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

        chattingRoomBack.setGui(this);
        chattingRoomBack.setUserInfo(nickName, roomName, ipAddress, portNum);
        chattingRoomBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.

    }

    public void appendMessage(String Message) {
        chatedArea.append(Message);
    }

    public void resetUserListArea(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        for (String nickName : nickNameList) {
            userListArea.append(nickName + "\n");
        }
    }
}