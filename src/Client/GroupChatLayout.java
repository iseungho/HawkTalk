package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
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
    private JPanel DrawPanel;
    JTextArea UserList;
    private JTextArea ChatTextArea;
    GroupChatBack groupChatBack = new GroupChatBack();
    BufferedImage imgBuff;
    Brush brush;


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
                    groupChatBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });
        ChatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Message = ChatField.getText().trim();
                if (Message.length() > 0) {
                    groupChatBack.sendMessage("[" + nickName + "]: " + Message + "\n");
                    ChatField.setText(null);
                }
            }
        });

        DrawPanel.setBackground(Color.WHITE);
        DrawPanel.setOpaque(true);

        imgBuff = new BufferedImage(DrawPanel.getWidth(), DrawPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);

        JLabel drawLabel = new JLabel(new ImageIcon(imgBuff));
        // drawLabel.setBounds(DrawPanel.getX(), DrawPanel.getY(), DrawPanel.getWidth(), DrawPanel.getHeight());
        drawLabel.setSize(DrawPanel.getSize());

        brush = new Brush();
        // brush.setBounds(DrawPanel.getX(), DrawPanel.getY(), DrawPanel.getWidth(), DrawPanel.getHeight());
        brush.getSize(DrawPanel.getSize());

        DrawPanel.add(drawLabel);
        DrawPanel.add(brush);

        drawLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                brush.setX(e.getX());
                brush.setY(e.getY());
                groupChatBack.sendMessage("!Drawing" + brush.x + ":" + brush.y);
                brush.repaint();
                brush.printAll(imgBuff.getGraphics());
            }
        });

        groupChatBack.setGui(this);
        groupChatBack.setUserInfo(nickName, roomName, ipAddress, portNum);
        groupChatBack.start(); // 채팅창이 켜짐과 동시에 접속을 실행해줍니다.
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

    public synchronized Brush getBrush() {
        return this.brush;
    }

    public synchronized void brushBuff() {
        brush.printAll(imgBuff.getGraphics());
    }
}
