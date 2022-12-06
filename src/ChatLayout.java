import javax.swing.*;

public class ChatLayout extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel ChatPanel;
    private JButton 전송Button;
    private JTextField ChatField;
    private JPanel ButtonPanel;
    private JPanel AllChatPanel;

    public ChatLayout(){
        setContentPane(ChatPanel);
        setSize(800, 600);
        setTitle("홍톡2.0");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        new ChatLayout();
    }
}
