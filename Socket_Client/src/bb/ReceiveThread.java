package bb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * this class is used to handle the messages received from server
 * using thread
 */

public class ReceiveThread extends Thread {
	Socket socket;
	String id;
	Stock_Client stock_client;
	
	public ReceiveThread(Socket skt, String id) {
		this.socket = skt;
		this.id = id;
		stock_client = null;
	}
	
	public ReceiveThread(Stock_Client sc) {
		stock_client = sc;
		this.socket = stock_client.getSocket();
		this.id = stock_client.getID();
	}

	@Override
	public void run() {
		try {
			// get input stream
			InputStream input = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			while (true) {
				String info = br.readLine();
				
				// formatting received info
				info = ProcessMsg.updateContext(stock_client, info);
				System.out.println("message from server: " + info);
				
				// print in the Console
				System.out.println(ProcessMsg.contextStr(stock_client));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
