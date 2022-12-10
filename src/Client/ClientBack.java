package Client;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientBack extends Thread {
	private String nickName, roomName, ipAddress, message;
	private int portNum;
	private ClientWaitingRoom clientWaitingRoom;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	ArrayList<String> nickNameList = new ArrayList<>(); // 유저목록을 저장합니다.
	ArrayList<String> roomNameList = new ArrayList<>();
	HashMap<String, ArrayList<String>> roomMap = new HashMap<>();

	public void setGui(ClientWaitingRoom clientWaitingRoom) {
		// 실행했던 ClientGUI 그 자체의 정보를 들고옵니다.
		this.clientWaitingRoom = clientWaitingRoom;
	}

	public void setUserInfo(String nickName, String ipAddress, int portNum) {
		// ClientGUI로부터 닉네임, 아이피, 포트 값을 받아옵니다.
		this.nickName = nickName;
		this.ipAddress = ipAddress;
		this.portNum = portNum;
	}

	@Override
	public void run() {
		// 서버 접속을 실행합니다.
		try {
			socket = new Socket(ipAddress, portNum);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(nickName);
			while (in != null) {
				// 임의의 식별자를 받아 닉네임 혹은 일반 메세지인지 등을 구분시킵니다.
				message = in.readUTF();
				if (message.contains("!ResetUserList")) {
					// !ResetUserList이라는 수식어가 붙어있을 경우엔 닉네임으로 간주합니다.
					clientWaitingRoom.userListArea.setText(null);
					nickNameList.add(message.substring(14));
					clientWaitingRoom.resetUserListArea(nickNameList);
				} else if (message.contains("!ResetRoomList")) {
					// clientWaitingRoom.roomModel.clear();
					clientWaitingRoom.roomModel.removeAllElements();
					clientWaitingRoom.roomList.removeAll();
					roomNameList.add(message.substring(14));
					clientWaitingRoom.resetRoomList(roomNameList);
				} else if (message.contains("님이 입장하셨습니다.") || message.contains("님이 퇴장하셨습니다.")) {
					// ~~ 님이 입장하셨습니다. 라는 식별자를 받으면 기존의 닉네임 리스트 초기화 후 새로 입력시킵니다.
					nickNameList.clear();
					roomNameList.clear();
					// clientWaitingRoom.userListArea.setText(null);
					clientWaitingRoom.appendMessage(message);
				} else if (message.contains("이(가) 생성되었습니다.")) {
					// ~~ 님이 퇴장하셨습니다. 라는 식별자를 받으면 기존의 닉네임 리스트 초기화 후 새로 입력시킵니다.
					roomNameList.clear();
					// clientWaitingRoom.userListArea.setText(null);
					clientWaitingRoom.appendMessage(message);
				} else {
					// 위 모든 값이 아닐 시엔 일반 메세지로 간주합니다.
					clientWaitingRoom.appendMessage(message);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendMessage(String message) {
		// 입력받은 값을 서버로 전송(out) 해줍니다.
		try {
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}