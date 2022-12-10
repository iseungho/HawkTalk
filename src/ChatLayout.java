import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatLayout extends JFrame{
    private JPanel ChatPanel;
    private JButton SendButton;
    private JTextField ChatField;
    private JCheckBox ProfileCheckBox;
    private JButton NewRoomButton;
    private JTextArea ChatArea;
    private JList RoomList;
    private JTextArea UserList;

    //파라미터: 색상, 선 두께, border의 모서리를 둥글게 할 것인지
    private LineBorder bb = new LineBorder(Color.black, 1, false);


    public ChatLayout(){
        setContentPane(ChatPanel);
        setSize(800, 600);
        setTitle("홍톡2.0");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ChatArea.setBorder(bb);
        RoomList.setBorder(bb);
        UserList.setBorder(bb);
        ProfileCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileLayout();
            }
        });
    }
    public static void main(String[] args) {
        new ChatLayout();
    }
}
