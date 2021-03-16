package aa;

import java.io.PrintStream;
import java.net.Socket;

/*
 * this class is used for server to send messages to client(s)
 * firstly, this class was used
 * but when updating and optimizing those functions of server,
 * this class is no longer used
 * just for testing when doing the implementation 
 */

public class ClientHandlerThreadWrite extends Thread {

	private Socket socket;

	public ClientHandlerThreadWrite(Socket socket) {
		this.socket = socket;
	}

	public void setSocket(Socket skt) {
		this.socket = skt;
	}

	@Override
	public void run() {
		try {
			// Scanner scanner = new Scanner(System.in);
			// get the output stream
			PrintStream ps = new PrintStream(socket.getOutputStream());
			// 
			while (true) {
				if (Stock_Server.flag) {
					// continue;
					ps.println(Stock_Server.flag + ", received");
				}

				// System.out.println("输入发送给客户端：");
				// String message = scanner.nextLine();

				// 

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