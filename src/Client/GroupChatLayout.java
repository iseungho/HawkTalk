package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GroupChatLayout extends JFrame{
    String nickName, ipAddress;
    int portNum;
    private JPanel GroupChatPanel;
    private JTextField ChatField;
    private JButton ImageButton;
    private JButton SendButton;
    private JButton DeleteButton;
    private JButton EraserButton;
    private JButton CircleButton;
    private JButton ColorButton;
    private JButton StraightButton;
    private JButton DrawButton;
    private JSpinner LineSpinner;
    private JButton SquareButton;
    private JTextPane ChatTextPane;
    private JPanel DrawField;
    JTextArea UserList;
    private JTextArea ChatTextArea;
    ChattingRoomBack chattingRoomBack = new ChattingRoomBack();


    public GroupChatLayout(String nickName, String roomName, String ipAddress, int portNum) {
        this.nickName = nickName;
        this.portNum = portNum;
        setContentPane(GroupChatPanel);
        setSize(1200, 700);
        setTitle(roomName);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (Message.length() > 0) {
                    chattingRoomBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });
        ChatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (Message.length() > 0) {
                    chattingRoomBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });
        chattingRoomBack.setGui(this);
        chattingRoomBack.setUserInfo(nickName, roomName, ipAddress, portNum);
        chattingRoomBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.
    }

    public void appendMessage(String Message) {
        ChatTextArea.append(Message);
    }

    public void resetUserListArea(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        for (String nickName : nickNameList) {
            UserList.append(nickName + "\n");
        }
    }
}