package langrensha.io.client.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.google.gson.Gson;

import langrensha.io.client.Client;
import langrensha.io.protocol.Msg01;
import langrensha.io.protocol.Send;

public class ClientWeb3 {
	static Gson gson = new Gson();

	public static void main(String[] args) throws UnknownHostException, IOException {

		f1();
	}

	public static void pw(Socket socket, String msg) {
		// BaseMsg baseMsg = new BaseMsg();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.println(msg);
	}

	/*
	 * 
	 * {"cmd":771,"msg":{"uid":8139720,"room_id":62}}
	 */
	public static void f1() {
		int uid = 8140348;
		int index = 9;
		String sid = "5e6a08a42c982073289a6f7ea6120d06";
		
		Send joinServer = new Send();
		Msg01 msg01 = new Msg01();
		joinServer.setCmd(760);
		msg01.setSid(sid);
		msg01.setUid(uid);
		joinServer.setMsg(msg01);

		Send joinRoom = new Send();
		Msg01 msg02 = new Msg01();
		joinRoom.setCmd(771);
		msg02.setRoom_id(62);
		msg02.setUid(uid);
		joinRoom.setMsg(msg02);

		Send sit = new Send();
		Msg01 msg03 = new Msg01();
		msg03.setIndex(index);
		sit.setCmd(772);
		msg03.setUid(uid);
		sit.setMsg(msg03);

		Send exitRoom = new Send();
		Msg01 msg04 = new Msg01();
		exitRoom.setCmd(773);
		msg04.setUid(uid);

		Send lineSpeak = new Send();
		lineSpeak.setCmd(774);

		Send exitLine = new Send();
		exitLine.setCmd(775);

		Scanner scanner = new Scanner(System.in);
		String string = null;
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
			String uri = "ws://192.168.0.161:9507";
			Session session = container.connectToServer(Client.class, new URI(uri)); // 连接会话

			session.getBasicRemote().sendText(gson.toJson(joinServer)); // 发送文本消息
			// session.getBasicRemote().sendText(gson.toJson(joinRoom));
			// session.getBasicRemote().sendText(gson.toJson(sit));
			do {
				System.out.println("输入要发送的数据:");
				string = scanner.nextLine();
				if (string.equals("joinServer")) {
					session.getBasicRemote().sendText(gson.toJson(joinServer)); // 发送文本消息
				} else if (string.equals("joinRoom")) {
					session.getBasicRemote().sendText(gson.toJson(joinRoom)); // 发送文本消息
				} else if (string.equals("sit")) {
					session.getBasicRemote().sendText(gson.toJson(sit)); // 发送文本消息
				} else if (string.equals("exitRoom")) {
					session.getBasicRemote().sendText(gson.toJson(exitRoom));
				} else if (string.equals("line")) {
					session.getBasicRemote().sendText(gson.toJson(lineSpeak));
				} else if (string.equals("exitLine")) {
					session.getBasicRemote().sendText(gson.toJson(exitLine));
				}
			} while (true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
