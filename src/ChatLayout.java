import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatLayout extends JFrame{
    private JPanel ChatPanel;
    private JButton 전송Button;
    private JTextField ChatField;
    private JList list1;
    private JTextArea textArea1;
    private JCheckBox ProfileCheckBox;
    private JButton 방생성Button;

    public ChatLayout(){
        setContentPane(ChatPanel);
        setSize(800, 600);
        setTitle("홍톡2.0");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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
