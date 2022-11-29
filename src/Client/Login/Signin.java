package Client.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signin extends JFrame {
    private JTextField tfId;
    private JPasswordField tfPwd;
    private JButton signin = new JButton("Sign In"), signup = new JButton("Sign Up");

    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;

    public Signin() { // 생성자
        setTitle("Signin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tfId = new JTextField(10);
        tfPwd = new JPasswordField(10);
        tfPwd.setEchoChar('*');

        // OK버튼과 TextField에 이벤트처리를 위한 Listener 추가
        // 엔터키를 눌렀을 때도 처리되게 하기 위해 TextField에 이벤트처리
        signin.addActionListener(new MyActionListener());
        tfId.addActionListener(new MyActionListener());
        tfPwd.addActionListener(new MyActionListener());
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Signup();
            }
        });

        GridLayout grid = new GridLayout(4, 1);
        grid.setVgap(10);

        setLayout(grid);

        JPanel idPanel = new JPanel(new FlowLayout());
        idPanel.add(new JLabel("        아이디 ", JLabel.RIGHT));
        idPanel.add(tfId);
        add(idPanel);

        JPanel pwdPanel = new JPanel(new FlowLayout());
        pwdPanel.add(new JLabel("비밀번호 ", JLabel.RIGHT));
        pwdPanel.add(tfPwd);
        add(pwdPanel);

        JPanel sInPanel = new JPanel();
        sInPanel.add(signin, JLabel.CENTER);
        add(sInPanel);

        JPanel sUpPanel = new JPanel();
        sUpPanel.add(signup, JLabel.CENTER);
        add(sUpPanel);

        setSize(250, 250);
        setVisible(true);
    }

    private StringBuilder sb;
    private String sql;
    private ResultSet srs = null;

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String id = tfId.getText();
            String pwd = tfPwd.getText();
            try {
                sb = new StringBuilder();
                sql = sb.append("select * from usertable where ID='").append(id).append("';").toString();
                srs = stmt.executeQuery(sql);
                if (srs.next()) {
                    if (srs.getString("Pwd").equals(pwd)) {
                        System.out.println("로그인 성공!");
                    } else {
                        System.out.println("비밀번호가 틀렸습니다");
                    }
                } else {
                    System.out.println("존재하지 않는 아이디입니다");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String args[]) {
        new Signin();
    }
}
