package bb;

/*
 * this class is to run client socket
 * you could run this class multiple times
 * every time you click run, a client is going to create a socket with unique id
 * and connect with server
 */

public class LaunchClient {
	
	
	public static void main(String[] args) {
		
		String id = Stock_Tools.generateID();
		new Stock_Client(id);
		
	}
	
}
