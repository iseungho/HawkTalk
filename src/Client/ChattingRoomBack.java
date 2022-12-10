package Client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChattingRoomBack extends Thread {
    private String nickName, roomName, message, ipAddress;
    private int portNum;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private ChattingRoom chattingRoom;
    ArrayList<String> nickNameList = new ArrayList<>(); // 유저목록을 저장합니다.

    public void setGui(ChattingRoom chattingRoom) {
        // 실행했던 ClientGUI 그 자체의 정보를 들고옵니다.
        this.chattingRoom = chattingRoom;
        this.chattingRoom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setUserInfo(String nickName, String roomName, String ipAddress, int portNum) {
        // ClientGUI로부터 닉네임, 아이피, 포트 값을 받아옵니다.
        this.nickName = nickName;
        this.roomName = roomName;
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
                    chattingRoom.userListArea.setText(null);
                    nickNameList.add(message.substring(14));
                    chattingRoom.resetUserListArea(nickNameList);
                } else if (message.contains("님이 입장하셨습니다.") || message.contains("님이 퇴장하셨습니다.")) {
                    // ~~ 님이 입장하셨습니다. 라는 식별자를 받으면 기존의 닉네임 리스트 초기화 후 새로 입력시킵니다.
                    nickNameList.clear();
                    // clientWaitingRoom.userListArea.setText(null);
                    chattingRoom.appendMessage(message);
                } else {
                    // 위 모든 값이 아닐 시엔 일반 메세지로 간주합니다.
                    chattingRoom.appendMessage(message);
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