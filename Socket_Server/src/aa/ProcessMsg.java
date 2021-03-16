package aa;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/*
 * this class is used for server to process messages when receiving them from client(s)
 * basically, two steps:
 * 1) first one is to update the information on server
 * 2) second one is to send the updated information to client(s)
 */

public class ProcessMsg extends Thread {

	// this method is used when testing
	// but after update and better measure taken
	// this method is not used here
	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);

			while (true) {
				System.out.println("输入发送给客户端：");
				String message = scanner.nextLine();
				Stock_Server.flag = true;
				if (Stock_Server.flag) {
					System.out.println(Stock_Server.flag + ", " + message);
					System.out.println("current size: " + Stock_Server.current.size());

					// send message to every socket
					for (int i = 0; i < Stock_Server.current.size(); i++) {
						sendMsg(Stock_Server.current.get(i), Stock_Server.flag + ", " + message);
					}
				}
				Stock_Server.flag = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// when message sent from client to server
	// server will process the message one by one, and synchronized lock is used here
	public static synchronized void process(Message msg, Socket socket) {
		try {
			
			// process message
			String message = msg.getMsg();
//			System.out.println("<<<<<<<<<<------------>>>>>>>>>" + message);
			Stock_Display.recordMsg(message); // record it

			if (msg.categorize() == 1) { // join type messages 
				String id = msg.parseID();

				joinProcess(msg, id, socket);
			} else if (msg.categorize() == 2) {// leave type messages 
				String id = msg.parseID();

				leaveProcess(msg, id, socket);
			} else if (msg.categorize() == 3) {// transact type messages 
				String[] ids = msg.parseTransact();
				
				transactProcess(msg, ids[0], ids[1], socket);
				
//				displayAllInfo();
			} else {
				// for testing
				System.out.println("no info processed");
//				displayAllInfo();
			}
			
			// display all info after updating the info on server
			displayAllInfo();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// when new trader joining
	// the first joining trader and other joining traders
		// if the first, assign the stock to that trader
		// if other joining traders, send them the background/context message 
			// such as who holds stock now and who are present in the market
	public static void joinProcess(Message msg, String id, Socket socket) {
		Stock_Display.joinID(id); // record

		// if this is the first trader
		if (Stock_Server.current.size() == 1) {
			Stock_DB.transact(id); // give stock to the first joining trader
			
			// send basic info to the new-joining trader
			synchronized (Stock_Server.mainEvent) {
//				Stock_Server.mainEvent = basicInfo();
				Stock_Server.mainEvent = formatMsg("", 6);

				sendMsg(socket, Stock_Server.mainEvent);

				Stock_Server.mainEvent = "";
			}
			
			// notify all
			synchronized (Stock_Server.mainEvent) {
//				Stock_Server.mainEvent = id + " " 
//						+ "is the first joining trader and has the stock";
				Stock_Server.mainEvent = formatMsg(id, 1);

				notifyAllUser(Stock_Server.mainEvent);

				Stock_Server.mainEvent = "";
			}
		}else {
			// send basic info to the new-joining trader
			synchronized (Stock_Server.mainEvent) {
//				Stock_Server.mainEvent = basicInfo();
				Stock_Server.mainEvent = formatMsg("", 6);

				sendMsg(socket, Stock_Server.mainEvent);

				Stock_Server.mainEvent = "";
			}
			
			// notify all
			synchronized (Stock_Server.mainEvent) {
//				Stock_Server.mainEvent = id + " join";
				Stock_Server.mainEvent = formatMsg(id, 2);
				notifyAllUser(Stock_Server.mainEvent);

				Stock_Server.mainEvent = "";
			}
		}
	}

	// when trader leaving
	// If the trader with the stock leaves the market,
		// the server gives the stock to one of the remaining traders
	// if trader without stock leaving, just notify all remaining traders
	public static void leaveProcess(Message msg, String id, Socket socket) {
		Stock_Display.leaveID(id); // record

		synchronized (Stock_DB.stockDB) {
			String holderID = Stock_DB.getCurrentHolderID();
			if (id.equals(holderID)) {
				// trader with stock leave
				Stock_DB.withStockLeave(); // back to server
				// If the trader with the stock leaves the market,
				// the server gives the stock to one of the remaining traders
				synchronized (Stock_Display.currentIDs) {
					String newID = Stock_Display.randomID();
					Stock_DB.transact(newID);

					// notify all user
					synchronized (Stock_Server.mainEvent) {
//						Stock_Server.mainEvent = id + " " + "leave, server give to " + newID;
						Stock_Server.mainEvent = formatMsg(id + " " + newID, 4);
						notifyAllUser(Stock_Server.mainEvent);

						Stock_Server.mainEvent = "";
					}
				}
			} else {
				// notify all user
				synchronized (Stock_Server.mainEvent) {
//					Stock_Server.mainEvent = id + " " + "leave";
					Stock_Server.mainEvent = formatMsg(id, 5);
					
					notifyAllUser(Stock_Server.mainEvent);

					Stock_Server.mainEvent = "";
				}
			}
		}

		// remove the socket of the leaving trader from the list
		synchronized (Stock_Server.current) {
			Stock_Server.current.remove(socket);
		}
	}
	
	
	// when transaction
	// trader with the stock can transact to the trader present in the market
	public static void transactProcess(Message msg, String id, String newHolderID, Socket socket) {
//		System.out.println(id + " ---------------- " + newHolderID);
		synchronized (Stock_DB.stockDB) {
			String holder = Stock_DB.getCurrentHolderID();
			if(holder.equals(id) && Stock_Display.isInMarket(newHolderID)) {
				Stock_DB.transact(newHolderID);

				//
				synchronized (Stock_Server.mainEvent) {
//					Stock_Server.mainEvent = id + " transact stock to " + newHolderID;
					Stock_Server.mainEvent = formatMsg("from " + id + " to " + newHolderID, 3);
					notifyAllUser(Stock_Server.mainEvent);

					Stock_Server.mainEvent = "";
				}
			}else {
				//
				synchronized (Stock_Server.mainEvent) {
//					Stock_Server.mainEvent = id + " transact unsuccessful";
					Stock_Server.mainEvent = formatMsg(id, 7);
					sendMsg(socket, Stock_Server.mainEvent);

					Stock_Server.mainEvent = "";
				}
			}
			
		}
	}
	
	// notify clients after server finishing processing the message
	// send message to all present traders
	public static synchronized void notifyAllUser(String msg) {
		// notify traders about new changes
		for (int i = 0; i < Stock_Server.current.size(); i++) {
			sendMsg(Stock_Server.current.get(i), msg);
		}
	}

	// send message to a certain trader present in the market
	public static synchronized void sendMsg(Socket socket, String msg) {
		try {
			// send message to the socket that is still connected and open
			if (socket.isConnected() && !socket.isClosed()) {
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// when a trader joining, send to the trader the information about:
		// the list of current traders
		// the holder of stock
	public static String basicInfo() {
		String str = "context ";
		
		str = str + Stock_DB.getCurrentHolderID() + " ";
		str = str + Stock_Display.currentTraders();
		
		return str;
	}
	
	// format the message to be sent to clients
	// there are 7 types
	public static String formatMsg(String msg, int type) {
		
		if(type == 1) {
			// the first trader joining
			msg = "first " + msg;
		}else if(type == 2) {
			// new trader joining
			msg = "join " + msg;
		}else if(type == 3) {
			// stock transact to new holder
			msg = "transact " + msg;
		}else if(type == 4) {
			// trader with stock leaving
			msg = "stock " + msg;
		}else if(type == 5) {
			// trader leaving
			msg = "leave " + msg;
		}else if(type == 6){
			msg = basicInfo();
		}else {
			msg = "operation unsuccessful " + msg;
		}
		
		return msg;
	}
	
	// display all info in the Console of server
	public static void displayAllInfo() {
		System.out.println("---------------------------");
		Stock_Display.displayInfo();
		Stock_DB.displayInfo();
		System.out.println("---------------------------");
	}
	
	
	// used for testing
//	public static void main(String[] args) {
//		String id = "666";
//		String newID = "888";
//		String newHolderID = "999";
//		
////		System.out.println(formatMsg("", 6));
//		System.out.println(formatMsg(id, 1));
//		System.out.println(formatMsg(id, 2));
//		System.out.println(formatMsg(id + " " + newID, 4));
//		System.out.println(formatMsg(id, 5));
//		System.out.println(formatMsg("from " + id + " to " + newHolderID, 3));
//		System.out.println(formatMsg(id, 7));
//	}
	
}