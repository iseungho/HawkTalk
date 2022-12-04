package Server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerBack extends Thread {
	Vector<ReceiveThread> clientlist = new Vector<>(); // 클라이언트의 쓰레드를 저장해줍니다.
	ArrayList<String> nickNameList = new ArrayList<>(); // 클라이언트의 닉네임을 저장해줍니다.
	ServerSocket serversocket;
	Socket socket;
	private ServerWaitingRoom serverWaitingRoom;

	public void setGUI(ServerWaitingRoom serverWaitingRoom) {
		this.serverWaitingRoom = serverWaitingRoom;
	}

	public void runServer(int portNum) {
		try {
			Collections.synchronizedList(clientlist); // 교통정리를 해준다.( clientList를 네트워크 처리해주는것 )
			serversocket = new ServerSocket(portNum); // 서버에 입력된 특정 Port만 접속을 허가하기 위해 사용했습니다.
			System.out.println("현재 아이피와 포트넘버는 [" + InetAddress.getLocalHost() + "], [" + portNum + "] 입니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			nickNameList.add("Server"); // 유저목록의 첫 번째 서버(Server)를 추가합니다.
			while (true) {
				System.out.println("새 접속을 대기합니다...");
				socket = serversocket.accept(); // 포트 번호와 일치한 클라이언트의 소켓을 받습니다.
				System.out.println("[" + socket.getInetAddress() + "]에서 접속하셨습니다.");
				ReceiveThread receive = new ReceiveThread(socket);
				clientlist.add(receive);
				receive.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void transmitAll(String Message) {
		// 모든 클라이언트들에게 메세지를 전송해줍니다.
		for (ReceiveThread client : clientlist) {
			try {
				client.sendMessage(Message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void removeClient(ReceiveThread Client, String nickName) {
		// 퇴장한 유저 발생시 목록에서 삭제하는 역할을 합니다.
		clientlist.removeElement(Client);
		nickNameList.remove(nickName);
		// System.out.println(nickName + "을 삭제 완료했습니다.");
		serverWaitingRoom.userList.setText(null);
		serverWaitingRoom.resetUserList(nickNameList);
	}

	class ReceiveThread extends Thread {
		// 각 네트워크(클라이언트)로부터 소켓을 받아 다시 내보내는 역할
		private DataInputStream in;
		private DataOutputStream out;
		String nickName, message;

		public ReceiveThread(Socket socket) {
			try {
				in = new DataInputStream(socket.getInputStream()); // Input
				out = new DataOutputStream(socket.getOutputStream()); // Output
				nickName = in.readUTF();
				nickNameList.add(nickName);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		@Override
		public void run() {
			try {
				// 새로운 유저 발생시 유저목록을 초기화한 후에 새롭게 유저목록을 입력해줍니다.
				// 또한, 새로운 유저가 입장했음을 모든 클라이언트에게 전송합니다.
				serverWaitingRoom.userList.setText(null);
				serverWaitingRoom.resetUserList(nickNameList);
				transmitAll("[서버]: " + nickName + "님이 입장하셨습니다.\n");
				for (String nickName : nickNameList) {
					// !ResetUserList은 해당 값이 닉네임임을 알게해주는 명령어
					transmitAll("!ResetUserList" + nickName);
				}
				serverWaitingRoom.appendMessage("[서버]: " + nickName + "님이 입장하셨습니다.\n");
				while (true) {
					message = in.readUTF();
					serverWaitingRoom.appendMessage(message);
					transmitAll(message);
				}
			} catch (Exception e) {
				// 유저가 접속을 종료하면 여기서 오류가 발생합니다.
				// 따라서 발생한 값을 다시 모든 클라이언트 들에게 전송시켜줍니다.
				// System.out.println(nickName + "님이 퇴장하셨습니다.");
				removeClient(this, nickName);
				transmitAll("[서버]: " + nickName + "님이 퇴장하셨습니다.\n");
				for (int i = 0; i < nickNameList.size(); i++) {
					transmitAll("!ResetUserList" + nickNameList.get(i));
				}
				serverWaitingRoom.appendMessage("[서버]: " + nickName + "님이 퇴장하셨습니다.\n");
			}
		}

		public void sendMessage(String message) {
			// 전달받은 값(Message)를 각 클라이언트의 쓰레드에 맞춰 전송합니다.
			try {
				out.writeUTF(message);
				out.flush();
			} catch (Exception e) {
				e.getStackTrace();
			}

		}
	}
}