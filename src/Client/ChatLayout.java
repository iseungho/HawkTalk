package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatLayout extends JFrame {
    private JPanel ChatPanel;
    ClientBack clientBack = new ClientBack();
    String nickName;
    private JButton SendButton;
    private JTextField ChatField;
    private JCheckBox ProfileCheckBox;
    private JButton NewRoomButton;
    private JTextArea ChatArea;
    DefaultListModel<String> roomModel;
    JList<String> RoomList;
    JTextArea UserList;
    JTextArea HelloArea;
    private JPanel helloPanel;
    private JLabel Label;
    private JButton WeatherButton;
    private ResultSet srs = null;
    PopupMenu pm = new PopupMenu();
    MenuItem pmItem1 = new MenuItem("Enter room");
    MenuItem pmItem2 = new MenuItem("Remove room");

    //파라미터: 색상, 선 두께, border의 모서리를 둥글게 할 것인지
    private LineBorder bb = new LineBorder(Color.black, 1, false);

    public String getNickName() {
        return this.nickName;
    }

    public void resetNickName(String newNickName) {
        this.nickName = newNickName;
    }

    public ChatLayout(String nickName, String ipAddress, int portNum){
        this.nickName = nickName;
        setContentPane(ChatPanel);
        setSize(800, 600);
        setTitle("HawkTalk");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ChatArea.setBorder(bb);
        RoomList.setBorder(bb);
        UserList.setBorder(bb);
        HelloArea.append(nickName + "님 어서오세요!");
        ProfileCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileLayout profileLayout = new ProfileLayout(clientBack.getNickName());
                profileLayout.setClientBack(clientBack);

            }
        });
        ChatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (Message.length() > 0) {
                    clientBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (e.getSource() == SendButton && Message.length() > 0) {
                    clientBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });
        roomModel = new DefaultListModel<>();
        RoomList.setModel(roomModel);
        NewRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String room = JOptionPane.showInputDialog("이름을 입력하세요.");
                if (room != null) {
                    clientBack.sendMessage("!CreateRoom" + room);
                }
            }
        });
        RoomList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    clientBack.sendMessage("!EnterRoom" + nickName + ":" + RoomList.getSelectedValue());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    // System.out.println(String.valueOf(e.getX()) + ", " + String.valueOf(e.getY()));
                    pm.show(RoomList, e.getX(), e.getY());
                }
            }
        });
        pm.add(pmItem1);
        pmItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RoomList.getSelectedValue() != null) {
                    clientBack.sendMessage("!EnterRoom" + nickName + ":" + RoomList.getSelectedValue());
                } else {
                    JOptionPane.showMessageDialog(null,"선택후 우클릭 해주세요");
                }
            }
        });

        pm.add(pmItem2);
        pmItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RoomList.getSelectedValue() != null) {
                    clientBack.sendMessage("!RemoveRoom" + RoomList.getSelectedValue());
                } else {
                    JOptionPane.showMessageDialog(null,"선택후 우클릭 해주세요");

                }
            }
        });
        this.add(pm);
        ChatField.addMouseListener(new MouseAdapter() {
        });
        ChatField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (ChatField.getText().equals("채팅을 입력하세요")) {
                    ChatField.setText("");
                }
            }
        });
        ChatField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (ChatField.getText().equals("채팅을 입력하세요")) {
                    ChatField.setText("");
                }
            }
        });
        WeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WeatherLayout();
            }
        });
        clientBack.setGui(this);
        clientBack.setUserInfo(nickName, ipAddress, portNum);
        clientBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.
    }
    public void appendMessage(String Message) {
        ChatArea.append(Message);
    }

    public void resetRoomList(ArrayList<String> roomNameList) {
        roomModel.removeAllElements();
        // System.out.println(roomNameList);
        for (String roomName : roomNameList) {
            roomModel.addElement(roomName);
        }
    }

    public void resetUserListArea(ArrayList<String> nickNameList) {
        // 유저목록을 유저리스트에 띄워줍니다.
        UserList.setText(null);
        for (String nickName : nickNameList) {
            UserList.append(nickName + "\n");
        }
    }
}
