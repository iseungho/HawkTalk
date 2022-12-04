package Server;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerEx {
	static int port = 8080;

	public static void main(String[] args) {
		new ServerWaitingRoom(port);
	}
}

class ServerWaitingRoom extends JFrame {
	// 서버용 채팅창
	JPanel serverPanel = new JPanel();
	JLabel userLabel = new JLabel("유저 목록");
	JTextField chatField = new JTextField(45);
	JButton sendBtn = new JButton("전송");
	TextArea serverChatList = new TextArea(30, 50);
	TextArea userList = new TextArea(30, 15);
	ServerBack serverBack = new ServerBack();

	public ServerWaitingRoom(int Port) {
		setTitle("메인 서버");
		setVisible(true);
		// setLocationRelativeTo(null);
		setSize(750, 600);
		// setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫았을 때 메모리에서 제거되도록 설정합니다.

		serverChatList.setEditable(false);
		userList.setEditable(false);

		chatField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				String Message = chatField.getText().trim();
				if (e.getKeyCode() == KeyEvent.VK_ENTER && Message.length() > 0) {
					appendMessage("[서버]: " + Message + "\n");
					serverBack.transmitAll("[서버]: " + Message + "\n");
					chatField.setText(null); // 채팅창 입력값을 초기화 시켜줍니다.
				}
			}
		});
		sendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String Message = chatField.getText().trim();
				if (e.getSource() == sendBtn && Message.length() > 0) {
					appendMessage("[서버]: " + Message + "\n");
					serverBack.transmitAll("[서버]: " + Message + "\n");
					chatField.setText(null); // 채팅창 입력값을 초기화 시켜줍니다.
				}
			}
		});

		serverPanel.add(serverChatList);
		serverPanel.add(userLabel);
		serverPanel.add(userList);
		serverPanel.add(chatField);
		serverPanel.add(sendBtn);
		add(serverPanel);

		userList.append("Server\n"); // 실행과 동시에 서버주인(Admin)을 유저목록에 추가하도록 합니다.
		serverBack.setGUI(this);
		serverBack.runServer(Port);
		serverBack.start(); // 서버 채팅창이 켜짐과 동시에 서버소켓도 함께 켜집니다.
	}

	public void appendMessage(String Message) {
		serverChatList.append(Message);
	}

	public void resetUserList(ArrayList<String> nickNameList) {
		for (String nickName : nickNameList) {
			userList.append(nickName + "\n");
		}
	}
}