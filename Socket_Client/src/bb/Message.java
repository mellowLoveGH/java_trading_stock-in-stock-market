package bb;
import java.util.regex.*;

/*
 * when client(s) sending messages server
 * those messages are formatted here before sending
 * 3 types messages: join, leave and transact
 * for "join", clients have to enter a message with a word "join" in the Console
 * for "leave", clients have to enter a message with a word "leave" in the Console
 * for "transact", clients have to enter a message with a word "transact" 
 * 	and the id of the new-holder within brackets in the Console
 * 	such as: transact (666)
 *  here "666" is the id of the new holder
 */

public class Message {
	
	String msg = "";
	
	public Message() {
	}
	
	// when client join
	public static String join(String id) {
		return id + " join";
	}
	
	// when client leave the market
	public static String leave(String id) {
		return id + " leave";
	}
	
	// when a trader doing a transaction
	// giving one stock to another trader
	public static String transact(String id, String counterparty_id) {
		return id + " " + counterparty_id + " transact";
	}
	
	public static String transact(String id, String c_id, String stock_id, String amount) {
		return id + " " + c_id + " " + stock_id + " " + amount;
	}
	
	// parse the entered message
	// categorize it as join, leave or transact
	public static String parseStr(String msg, String id) {
		if(msg.contains( "join" )) {
			return join(id);
		}else if(msg.contains( "leave" )) {
			return leave(id);
		}else if(msg.contains( "transact" )) {
			msg = msg.trim();
			String counterparty_id = parseNumber(msg, 3);
//			String stock_id = parseNumber(msg, 6);
//			String stock_number = parseNumber(msg, 2);
			
//			return transact(id, counterparty_id, stock_id, stock_number);
			
			if(counterparty_id.length() > 0) {
				return transact(id, counterparty_id);
			}
			
			return "error";
		}else{
			return "error";
		}
	}
	
	// parse n-digit number from string
	public static String parseNumber(String s, int n) {
		String num = "";
		Pattern patt = Pattern.compile("\\(\\d{" + n + "}\\)");
		Matcher match = patt.matcher(s);
		while(match.find()) {
			num = match.group();
		}
		// remove brackets
		int len = num.length();
		if(num.length() > 0) {
			return num.substring(1, len - 1);
		}
		return num;
	}
	
//	public static void main(String[] args) {
////		String msg = "give stock to 888";
////		
////		System.out.println(parseStr(msg, "666"));
//		String s = "Frequency/FA ID (6) VerifiedFA0 FAID5(125)/FA1 FAID7(175)/FA2 FAID1(476789)";
//		System.out.println(parseNumber(s, 3));
//		
//	}
	
}
