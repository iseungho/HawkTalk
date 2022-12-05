package Client.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class Signup extends JFrame {
    private JLabel lid, lpwd, lSign = new JLabel("Press Ok Button to Sign Up", JLabel.CENTER);
    private JTextField tfId, tfName, tfClassNumber, tfNickName;
    private JPasswordField tfPwd = new JPasswordField();
    private JButton okBtn = new JButton("회원가입");

    private JDBCconnector jc = new JDBCconnector();
    private Statement stmt = jc.stmt;
    private ResultSet srs = jc.srs;

    public Signup() { // 생성자
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridLayout grid = new GridLayout(8, 1);
        grid.setVgap(5);

        Container c = getContentPane();
        c.setLayout(grid);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("        이름 ", JLabel.RIGHT));
        tfName = new JTextField(10);
        namePanel.add(tfName);
        c.add(namePanel);

        JPanel classNumberPanel = new JPanel(new FlowLayout());
        classNumberPanel.add(new JLabel("        학번 ", JLabel.RIGHT));
        tfClassNumber = new JTextField(10);
        classNumberPanel.add(tfClassNumber);
        c.add(classNumberPanel);

        JPanel nickPanel = new JPanel(new FlowLayout());
        nickPanel.add(new JLabel("    닉네임 ", JLabel.RIGHT));
        tfNickName = new JTextField(10);
        nickPanel.add(tfNickName);
        c.add(nickPanel);

        JPanel idPanel = new JPanel(new FlowLayout());
        idPanel.add(new JLabel("            ID ", JLabel.RIGHT));
        tfId = new JTextField(10);
        idPanel.add(tfId);
        c.add(idPanel);

        JPanel pwPanel = new JPanel(new FlowLayout());
        pwPanel.add(new JLabel("비밀번호 ", JLabel.RIGHT));
        tfPwd.setEchoChar('*');
        tfPwd.setColumns(10);
        pwPanel.add(tfPwd);
        c.add(pwPanel);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                if (tfName.getText().equals("")) {
                    flag = false;
                    lSign.setText("이름을 입력하세요");
                } else if (tfClassNumber.getText().equals("")) {
                    flag = false;
                    lSign.setText("학번을 입력하세요");
                } else if (tfNickName.getText().equals("")) {
                    flag = false;
                    lSign.setText("닉네임을 입력하세요");
                } else if (tfId.getText().equals("")) {
                    flag = false;
                    lSign.setText("아이디를 입력하세요");
                } else if (tfPwd.getText().equals("")) {
                    flag = false;
                    lSign.setText("비밀번호를 입력하세요");
                }
                if (!flag) return;
                ArrayList<String> userData = new ArrayList<>();
                userData.add(tfName.getText());
                userData.add(tfClassNumber.getText());
                userData.add(tfNickName.getText());
                userData.add(tfId.getText());
                userData.add(tfPwd.getText());
                try {
                    int a = jc.isValid(stmt, userData);
                    // System.out.println(a);
                    switch (a) {
                        case 1 -> {
                            lSign.setText("이미 가입되어 있는 사용자입니다");
                            flag = false;
                        }
                        case 2 -> {
                            lSign.setText("이미 사용중인 닉네임입니다");
                            flag = false;
                        }
                        case 3 -> {
                            lSign.setText("이미 사용중인 ID입니다");
                            flag = false;
                        }
                        default -> flag = true;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (!flag) return;
                lSign.setText("회원가입 성공!");
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("insert into usertable (Name, ClassNumber, NickName, ID, Pwd) values ('").append(userData.get(0))
                            .append("', '").append(userData.get(1).toUpperCase()).append("', '").append(userData.get(2)).append("', '")
                            .append(userData.get(3)).append("', '").append(userData.get(4)).append("');");
                    String sql = sb.toString();
                    stmt.executeUpdate(sql); // 레코드 추가
                    jc.printTable(stmt);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        c.add(lSign);

        JPanel okPanel = new JPanel();
        okPanel.add(okBtn, JLabel.CENTER);
        c.add(okPanel);

        setSize(250, 350);
        setVisible(true);
    }

    public static void main(String args[]) {
        new Signup();
    }
}
