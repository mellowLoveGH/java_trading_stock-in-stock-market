package bb;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * this class is for tools when designing
 * but not used very much
 */

public class Stock_Tools {
	
	public static List<String> IDs = new LinkedList<>();
	public static Random ran = new Random();
	// when a new trader join, there should be a new ID assigned to this trader
	public static String generateID() {
		// here just assume there are only 3-digit ID 
		String id = ran.nextInt(9) + "" + ran.nextInt(9) + "" + ran.nextInt(9);
		while(IDs.contains(id)) {
			id = ran.nextInt(9) + "" + ran.nextInt(9) + "" + ran.nextInt(9);
		}
		IDs.add(id);
		
		return id;
	}
	
	
//	public static void main(String[] args) {
//		
//		// testing generateID();
//		for (int i = 0; i < 900; i++) {
//			generateID();
//		}
//		
//		for (int i = 0; i < IDs.size(); i++) {
//			System.out.println(IDs.get(i));
//		}
//		
//	}
//	
}
