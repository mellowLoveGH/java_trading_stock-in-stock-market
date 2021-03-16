package aa;

/*
 * this class is to create a server with socket, 
 * generally speaking, only run this class once
 */

public class LaunchServer {
	
	public static void main(String[] args) {
		
		Stock_Server ss = new Stock_Server();
		ss.start();
	}
	
}
