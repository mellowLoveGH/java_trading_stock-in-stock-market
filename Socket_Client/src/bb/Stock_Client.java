package bb;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/*
 * this class is the client to receive and send messages from server
 * using thread
 */

public class Stock_Client extends Thread {
	
	private Socket socket;
	private String id;
	
	private List<String> context;
	
	public Stock_Client(String ID) {
		try {
			socket = new Socket("127.0.0.1", 8888);
			id = ID;
			context = new LinkedList<String>();
			
			// running
			ReceiveThread rt = new ReceiveThread(this);
			SendThread st = new SendThread(this);
			
			rt.start();
			st.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public String getID() {
		return id;
	}
	
	public List<String> getContext(){
		return context;
	}
	
//	public String updateContext(String msg) {
//		if(msg.startsWith("context")) {
//			msg = msg.replace("context", "").trim();
//			String[] ids = msg.split(" ");
//			context.clear();
//			for (int i = 0; i < ids.length; i++) {
//				context.add(ids[i]);
//			}
//			
//			return contextStr();
//		}
//		
//		return msg;
//	}
//	
//	public String contextStr() {
//		if(context.size() == 0)
//			return "";
//			
//		String str = "";
//		
//		str = "\ncurrent holder of the stock: \n" + context.get(0);
//		str = str + "\n" + "current trader(s) in the market: ";
//		
//		for (int i = 1; i < context.size(); i++) {
//			str = str + "\n" + context.get(i);
//		}
//		
//		str = str.replace(id, "myself");
//		return str;
//	}
	
	
//	public static void main(String[] args) {
////		Scanner scanner = new Scanner(System.in);
//		String id = Stock_Tools.generateID();
//		try {
//			// 1、准备Socket，连接服务器，需要指定服务器的IP地址和端口号
////			Socket socket = new Socket("127.0.0.1", 8888);
//
////			// 2、获取输出流，用来发送数据给服务器
////			OutputStream out = socket.getOutputStream();
////			PrintStream ps = new PrintStream(out);
////			// 3、获取输入流，用来接收服务器发送给该客户端的数据
////			InputStream input = socket.getInputStream();
////			BufferedReader br = new BufferedReader(new InputStreamReader(input));
//
////			while (true) {
////				System.out.println("输入发送给服务器的单词或成语：");
////				String message = scanner.nextLine();
////				if (message.equals("stop")) {
////					socket.shutdownOutput();
////					break;
////				}
////				// 4、 发送数据
////				ps.println(message);
////				// 接收数据
////				String feedback = br.readLine();
////				System.out.println("从服务器收到的反馈是：" + feedback);
////			}
//			
////			ReceiveThread rt = new ReceiveThread(socket, id);
////			SendThread st = new SendThread(socket, id);
////			rt.start();
////			st.start();
//
////			socket.close();
////			scanner.close();
//			
//			new Stock_Client(id);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
}

