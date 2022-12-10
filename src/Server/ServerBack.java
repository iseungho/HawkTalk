package Server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerBack extends Thread {
	private int portNum;
	ServerSocket serversocket;
	Vector<ReceiveThread> clientThreadList = new Vector<>(); // 클라이언트의 쓰레드를 저장해줍니다.
	ArrayList<String> nickNameList = new ArrayList<>();
	ArrayList<String> roomNameList = new ArrayList<>();
	HashMap<String, ServerBack> roomMap = new HashMap<>();
	Socket socket;

	public ServerBack(int portNum) {
		this.portNum = portNum;
		runServer();
		start();
	}

	public void runServer() {
		try {
			Collections.synchronizedList(clientThreadList); // 교통정리를 해준다.( clientList를 네트워크 처리해주는것 )
			serversocket = new ServerSocket(portNum); // 서버에 입력된 특정 Port만 접속을 허가하기 위해 사용했습니다.
			System.out.println("현재 아이피와 포트넘버는 [" + InetAddress.getLocalHost() + "], [" + portNum + "] 입니다.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void run() {
		try {
			nickNameList.add("Admin"); // 유저목록의 첫 번째 서버(Admin)를 추가합니다.
			while (true) {
				System.out.println("새 접속을 대기합니다...");
				socket = serversocket.accept(); // 포트 번호와 일치한 클라이언트의 소켓을 받습니다.
				System.out.println("[" + socket.getInetAddress() + "]에서 접속하셨습니다.");
				ReceiveThread receiveThread = new ReceiveThread(socket);
				clientThreadList.add(receiveThread);
				receiveThread.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendAll(String message) {
		// 모든 클라이언트들에게 메세지를 전송해줍니다.
		for (ReceiveThread clientThread : clientThreadList) {
			try {
				clientThread.sendMessage(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void removeClient(ReceiveThread Client, String nickName) {
		// 퇴장한 유저 발생시 목록에서 삭제하는 역할을 합니다.
		clientThreadList.removeElement(Client);
		nickNameList.remove(nickName);
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
				System.out.println(nickName);
				nickNameList.add(nickName);
				System.out.println(nickNameList);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		public void run() {
			try {
				// 새로운 유저 발생시 유저목록을 초기화한 후에 새롭게 유저목록을 입력해줍니다.
				// 또한, 새로운 유저가 입장했음을 모든 클라이언트에게 전송합니다.
				sendAll("[서버]: " + nickName + "님이 입장하셨습니다.\n");
				for (String nickName : nickNameList) {
					// !ResetUserList은 해당 값이 닉네임임을 알게해주는 명령어
					sendAll("!ResetUserList" + nickName);
				}
				for (String roomName : roomNameList) {
					sendAll("!ResetRoomList" + roomName);
				}
				while (true) {
					message = in.readUTF();
					if (message.contains("!CreateRoom")) {
						String room = message.substring(11);
						if (!roomMap.containsKey(room)) {
							sendAll("[서버]: 채팅방 " + room + "이(가) 생성되었습니다.\n");
							roomNameList.add(room);
							// System.out.println(portNum + roomMap.size() + 1);
							roomMap.put(room, new ServerBack(portNum + roomMap.size() + 1));
							for (String roomName : roomNameList) {
								sendAll("!ResetRoomList" + roomName);
							}
						} else {
							sendAll("[서버]: 채팅방 " + room + "은(는) 이미 존재하는 채팅방입니다.\n");
						}
					} else if (message.contains("!RemoveRoom")) {
						String room = message.substring(11);
						sendAll("[서버]: 채팅방 " + room + "이(가) 제거되었습니다.\n");
						roomNameList.remove(room);
						roomMap.remove(room);
						for (String roomName : roomNameList) {
							sendAll("!ResetRoomList" + roomName);
						}
					} else {
						sendAll(message);
					}
				}
			} catch (Exception e) {
				// 유저가 접속을 종료하면 여기서 오류가 발생합니다.
				// 따라서 발생한 값을 다시 모든 클라이언트 들에게 전송시켜줍니다.
				// System.out.println(nickName + "님이 퇴장하셨습니다.");
				removeClient(this, nickName);
				sendAll("[서버]: " + nickName + "님이 퇴장하셨습니다.\n");
				for (String nickName : nickNameList) {
					sendAll("!ResetUserList" + nickName);
				}
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

	public static void main(String[] args) {
		new ServerBack(8080);
	}
}