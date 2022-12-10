package Client;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientWaitingRoom extends JFrame {
    //클라이언트용 채팅창
    String nickName;
    ClientBack clientBack = new ClientBack();
    JPanel clientPanel = new JPanel();
    JLabel userLabel = new JLabel("유저 목록");
    JLabel User = new JLabel(nickName);
    JTextField chatField = new JTextField(45);
    JButton sendBtn = new JButton("전송");

    JLabel roomLabel = new JLabel("채팅방 목록");
    DefaultListModel<String> roomModel;
    JList<String> roomList;
    JButton createNewRoomBtn = new JButton("채팅방 생성");

    TextArea chatedList = new TextArea(30, 50);
    TextArea userListArea = new TextArea(30, 15);

    public ClientWaitingRoom(String nickName, String ipAddress, int portNum) {
        this.nickName = nickName;
        setTitle("HawkTalk");
        setVisible(true);
        // setLocationRelativeTo(null);
        setSize(750, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatedList.setEditable(false);
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

        clientPanel.add(User);
        clientPanel.add(chatedList);
        clientPanel.add(userLabel);
        clientPanel.add(userListArea);
        clientPanel.add(chatField);
        clientPanel.add(sendBtn);
        add(clientPanel);

        JPanel roomPanel = new JPanel(new BorderLayout());
        roomLabel.setHorizontalAlignment(JLabel.CENTER);
        roomPanel.add(roomLabel);
        roomModel = new DefaultListModel<>();
        roomList = new JList<>(roomModel);
        roomPanel.add(roomList, BorderLayout.CENTER);
        roomList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    String roomName = roomList.getSelectedValue();
                    // clientBack.sendMessage("[서버]: " + nickName + "님이 " + roomName + "에 입장하셨습니다.\n");
                    new ChattingRoom(nickName, roomName, ipAddress, portNum + roomList.getSelectedIndex() + 1);
                }
            }
        });
        createNewRoomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String room = JOptionPane.showInputDialog("이름을 입력하세요.");
                if (room != null) {
                    clientBack.sendMessage("!CreateRoom" + room);
                }
            }
        });
        roomPanel.add(createNewRoomBtn, BorderLayout.SOUTH);

        clientPanel.add(roomPanel);
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

    public void resetRoomList(ArrayList<String> roomNameList) {
        roomModel.removeAllElements();
        for (String roomName : roomNameList) {
            roomModel.addElement(roomName);
        }
    }

    public void resetUserListArea(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        userListArea.setText(null);
        for (String nickName : nickNameList) {
            userListArea.append(nickName + "\n");
        }
    }
}