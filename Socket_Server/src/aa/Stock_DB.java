package aa;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * this class is used to record the stock
 * especially when transaction
 * 
 * in this project, assume there is only one type of stock
 * and the stock only has two attributes: holder ID and amount
 */
public class Stock_DB {

	// String, is holder ID
	// Integer, is amount
	// this is very scalable when multiple stocks are allowed
	// but in this project, only a single stock, so the integer is always 1
	public static Map<String, Integer> stockDB;
	
	// all clients are 3-digit number
	// but the server's id is quite different
	public static String serverID = "server";
	
	
	// record all transaction history
	public static List<String> transactInfo = new LinkedList<String>();
	
	
	// initialize
	public static Map<String, Integer> getStockDB() {
		if (stockDB == null) {
			stockDB = new TreeMap<>();
		}

		return stockDB;
	}

	// when initializing, generate a certain amount of stock
	// and all of those stock are assigned to the server
	public static void init() {
		int amount = 1;
		getStockDB();
		
		transactInfo.add(serverID);
		System.out.println("stock generated and assigned to server");
		stockDB.put(serverID, amount);
	}

	// when transaction, if there is a clearly pointed receiver
	public static void transact(String id) {
		synchronized (stockDB) {
			stockDB.clear(); // remove the previous holder

			stockDB.put(id, 1); // add new holder's ID
			
			transactInfo.add(id);
		}
	}

	// when the first trader joining, give the stock to this trader
	public static void firstJoin(String id) {
		synchronized (stockDB) {
			stockDB.clear(); // remove the previous holder

			stockDB.put(id, 1); // add new holder's ID
			
			transactInfo.add(id);
		}
	}

	// when the first trader joining, give the stock back to server
	public static void withStockLeave() {
		synchronized (stockDB) {
			stockDB.clear(); // remove the previous holder

			stockDB.put(serverID, 1); // add new holder's ID
			
			transactInfo.add(serverID);
		}
	}
	
	public static String getCurrentHolderID() {
		String holderID = (String) stockDB.keySet().toArray()[0];
		return holderID;
	}
	
	public static void displayInfo() {
//		System.out.println("---------------------------");
		System.out.println("stock holding info: ");
		for (int i = 0; i < transactInfo.size() - 1; i++) {
			System.out.println(transactInfo.get(i) + " ever held the stock");
		}
		System.out.println("current holder of the single stock:\t" + getCurrentHolderID());
	}
	
//	public static void main(String[] args) {
//		init();
//		System.out.println(getCurrentHolderID());
//		transact("666");
//		System.out.println(getCurrentHolderID());
//		withStockLeave();
//		System.out.println(getCurrentHolderID());
//		firstJoin("888");
//		System.out.println(getCurrentHolderID());
//	}
	
	
}
