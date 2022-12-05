package Client.Login;

import Client.ClientWaitingRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signin extends JFrame {
    private JTextField tfId = new JTextField("아이디를 입력하세요", 12);
    private JPasswordField tfPwd = new JPasswordField(12);
    private JButton signin = new JButton("로그인"), signup = new JButton("회원가입");

    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;

    class SignInPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon icon = new ImageIcon("imgs/signinback.png");
            Image img = icon.getImage();
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (tfId.getText().equals("")) {
                        tfId.setText("아이디를 입력하세요");
                    }
                }
            });
        }
    }

    public Signin() { // 생성자
        setTitle("Signin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // tfId = new JTextField(10);
        // tfPwd = new JPasswordField(10);

        // OK버튼과 TextField에 이벤트처리를 위한 Listener 추가
        // 엔터키를 눌렀을 때도 처리되게 하기 위해 TextField에 이벤트처리
        signin.addActionListener(new MyActionListener());

        tfId.addActionListener(new MyActionListener());
        tfId.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (tfId.getText().equals("아이디를 입력하세요")) {
                    tfId.setText("");
                }
            }
        });
        tfId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (tfId.getText().equals("아이디를 입력하세요")) {
                    tfId.setText("");
                }
            }
        });

        tfPwd.addActionListener(new MyActionListener());
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Signup();
            }
        });

        setContentPane(new SignInPanel());

        setLayout(new GridLayout(10, 1, 10, 10));

        for (int i = 0; i < 4; i++) {
            add(new JLabel());
        }

        JPanel signPanel = new JPanel(new FlowLayout());
        JPanel gridPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        gridPanel.add(tfId);
        gridPanel.add(tfPwd);
        // tfPwd.setEchoChar('*');
        signPanel.add(gridPanel);
        signPanel.setOpaque(false);
        add(signPanel);

        JPanel sPanel = new JPanel();
        sPanel.add(signup, JLabel.CENTER);
        sPanel.add(signin, JLabel.CENTER);
        sPanel.setOpaque(false);
        add(sPanel);

        setSize(400, 600);
        setResizable(false);
        setVisible(true);
    }

    private StringBuilder sb;
    private String sql;
    private ResultSet srs = null;

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String id = tfId.getText();
            if (id.equals("아이디를 입력하세요")) {
                System.out.println("아이디를 입력하세요");
                return;
            }
            String pwd = tfPwd.getText();
            try {
                sb = new StringBuilder();
                sql = sb.append("select * from usertable where ID='").append(id).append("';").toString();
                srs = stmt.executeQuery(sql);
                if (srs.next()) {
                    if (srs.getString("Pwd").equals(pwd)) {
                        new ClientWaitingRoom(srs.getString("NickName"), "localhost", 8080);
                        setVisible(false);
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