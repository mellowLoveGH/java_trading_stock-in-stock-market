package bb;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/*
 * this class is used for client(s) to send messages to server
 * using thread
 */

public class SendThread extends Thread {
	Socket socket;
	String id;
	public SendThread(Socket skt, String id) {
		this.socket = skt;
		this.id = id;
	}
	
	public SendThread(Stock_Client sc) {
		this.socket = sc.getSocket();
		this.id = sc.getID();
	}

	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			// get message from server
			OutputStream out = socket.getOutputStream();
			PrintStream ps = new PrintStream(out);

			while (true) {
				System.out.println("enter the message to be sent to server --- ");
				String message = scanner.nextLine(); // get data from Console
				
				// after typing in leave and server gets it, 
				// then typing close to shutdown the socket of this client 
				if(message.contains("close")) {
					scanner.close();
					socket.close();
					
					break;
				}
				
				message = Message.parseStr(message, id);
				if(message.equals("error")) {
					// when not parsing entered messages
					System.out.println("operation unsuccessful");
				}else {
					// send data
					System.out.println(message);
//					ps.println("from " + socket.getLocalPort() + "\t" + message);
					ps.println(message);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
