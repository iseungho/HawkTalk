import javax.swing.*;

public class ProfileLayout extends JFrame{
    private JButton ChangeNickButton;
    private JButton ChangePassButton;
    private JButton WithdrawalButton;
    private JPanel ProfilePanel;

    public ProfileLayout(){
        setContentPane(ProfilePanel);
        setSize(250,350);
        setTitle("프로필 수정");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
