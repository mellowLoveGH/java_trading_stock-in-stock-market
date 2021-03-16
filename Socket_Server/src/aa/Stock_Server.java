package aa;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/*
 * this class is the server, when launching, it listens to the client(s)
 */

public class Stock_Server extends Thread{
	
	// used to close server
	public static boolean flag = true;
	
	
	public static String MSG = "";
	public static String mainEvent = "";

	// the current traders that still connecting with server
	// public static Map<String, Socket> current = new TreeMap<String, Socket>();
	public static List<Socket> current = new LinkedList<Socket>();

	// used as message to help synchronize the message processing
	public static Message msg = new Message();
	
	
	private ServerSocket server;
	int count = 0;
	// 
	public Stock_Server() {
		
		try {
			server = new ServerSocket(8888);
			
			
			Stock_DB.getStockDB(); // initialize stock
			
			System.out.println("waiting connections...");
			count = 0;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	// running
	@Override
	public void run() {
		
		try {
			while(flag) {
				
				Socket socket = server.accept();
				System.out.println("No." + ++count + " client connect successfully!");
				current.add(socket);
				
				ClientHandlerThreadRead cr = new ClientHandlerThreadRead(socket);
				cr.start();
			}
			
			closeServer();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void closeServer() {
		try {
			server.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public ServerSocket getServer() {
		return server;
	}
	
	
//	public static void main(String[] args) {
//		try {
//			// ServerSocket
//			@SuppressWarnings("resource")
//			ServerSocket server = new ServerSocket(8888);
//			Stock_DB.getStockDB(); // initialize stock
//			System.out.println("waiting connections...");
//
//			int count = 0;
//			while (true) {
//				flag = false;
//				// listen
//				Socket socket = server.accept();
//				System.out.println("No." + ++count + " client connect successfully!");
//				current.add(socket);
//				// ClientHandlerThread ct = new ClientHandlerThread(socket);
//				// ct.start();
//				// WriteToClients wc = new WriteToClients();
//				// wc.start();
//				ClientHandlerThreadRead cr = new ClientHandlerThreadRead(socket);
//				// ClientHandlerThreadWrite ct = new ClientHandlerThreadWrite(socket);
//				cr.start();
//				// ct.start();
//				// wc.join(100 * 10);
//			}
//			//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
}

