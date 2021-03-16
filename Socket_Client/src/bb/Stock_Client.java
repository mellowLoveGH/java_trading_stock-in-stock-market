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
//			// 1��׼��Socket�����ӷ���������Ҫָ����������IP��ַ�Ͷ˿ں�
////			Socket socket = new Socket("127.0.0.1", 8888);
//
////			// 2����ȡ������������������ݸ�������
////			OutputStream out = socket.getOutputStream();
////			PrintStream ps = new PrintStream(out);
////			// 3����ȡ���������������շ��������͸��ÿͻ��˵�����
////			InputStream input = socket.getInputStream();
////			BufferedReader br = new BufferedReader(new InputStreamReader(input));
//
////			while (true) {
////				System.out.println("���뷢�͸��������ĵ��ʻ���");
////				String message = scanner.nextLine();
////				if (message.equals("stop")) {
////					socket.shutdownOutput();
////					break;
////				}
////				// 4�� ��������
////				ps.println(message);
////				// ��������
////				String feedback = br.readLine();
////				System.out.println("�ӷ������յ��ķ����ǣ�" + feedback);
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

