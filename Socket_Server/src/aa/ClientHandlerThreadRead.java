package aa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * this class is used for server to deal with the messages sent from client(s)
 * using thread
 */

public class ClientHandlerThreadRead extends Thread {
	private Socket socket;

	public ClientHandlerThreadRead(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// get the input stream, receiving messages from client(s)
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String str;
			// read those data
			while ((str = br.readLine()) != null) {
				// 
				StringBuilder word = new StringBuilder(str);
				// print the received data in the Console
				System.out.println(word.toString());

				// when receiving message from client
				// here is a lock-block to process the message
				synchronized (Stock_Server.msg) {
					Stock_Server.msg.setMsg(word.toString());
					// process
					ProcessMsg.process(Stock_Server.msg, socket);
					//
					Stock_Server.msg.setMsg("msg processed");
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void close() {
		try {
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
