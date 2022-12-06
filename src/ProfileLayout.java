import javax.swing.*;

public class ProfileLayout extends JFrame{
    private JButton 닉네임변경Button;
    private JButton 비밀번호변경Button;
    private JButton 회원탈퇴Button;
    private JPanel ProfilePanel;

    public ProfileLayout(){
        setContentPane(ProfilePanel);
        setSize(250,350);
        setTitle("프로필 수정");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new ProfileLayout();
    }
}
