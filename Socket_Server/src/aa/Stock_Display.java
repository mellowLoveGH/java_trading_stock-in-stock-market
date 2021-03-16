package aa;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * this class is used to 
 * record all activities between traders when present in the market
 * record all current present traders
 * record all ever joining traders
 */

public class Stock_Display {
	// record all activities
	public static List<String> historyMsg = new LinkedList<>(); 
	
	// record all current present traders
	public static List<String> currentIDs = new LinkedList<>();

	// record all ever joining traders
	public static List<String> traderIDs = new LinkedList<>();

	public static String serverID = "server";
	
	// when trader with stock leaving, choose randomly one of the remaining traders
	public static Random ran = new Random();

	// record every history info
	public static void recordMsg(String msg) {
		historyMsg.add(msg);
	}

	// when traders joining, record its id
	public static void joinID(String id) {
		traderIDs.add(id);
		currentIDs.add(id);
	}

	// when trader leaving, remove it from current IDs
	public static void leaveID(String id) {
		currentIDs.remove(id);
	}
	
	// check whether the trader is still in the market or not
	public static boolean isInMarket(String id) {
		return currentIDs.contains(id);
	}

	// get random one id of the remaining traders
	public static String randomID() {
		if (currentIDs.size() == 0) {
			return serverID;
		}

		int len = currentIDs.size();
		return currentIDs.get(ran.nextInt(len));
	}
	
	// format the id list into a string
	public static String currentTraders() {
		String str = "";

		for (int i = 0; i < currentIDs.size(); i++) {
			str = str + currentIDs.get(i) + " ";
		}

		return str.trim();
	}

	// display info
	public static void displayInfo() {
		// System.out.println("---------------------------");
		System.out.println("history messages: ");
		for (int i = 0; i < historyMsg.size(); i++) {
			System.out.println(historyMsg.get(i));
		}

		System.out.println("all traders ever joining: ");
		for (int i = 0; i < traderIDs.size(); i++) {
			System.out.println(traderIDs.get(i));
		}

		System.out.println("all current traders: ");
		for (int i = 0; i < currentIDs.size(); i++) {
			System.out.println(currentIDs.get(i));
		}

	}

}
