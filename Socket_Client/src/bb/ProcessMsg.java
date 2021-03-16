package bb;

import java.util.List;

/*
 * this class is used for client(s) to process the messages received from server
 * 1) update the info about new holder or the present traders in the market
 * 2) encapsulate the info in order to show the info in the Console
 */

public class ProcessMsg {
	
	// update info
	// 
	public static String updateContext(Stock_Client sc, String msg) {
		List<String> context = sc.getContext();
		
		// context/background info receiving from server when joining
		if(msg.startsWith("context")) {
			msg = msg.replace("context", "").trim();
			String[] ids = msg.split(" ");
			
			context.clear();
			for (int i = 0; i < ids.length; i++) {
				context.add(ids[i]);
			}
			
			return "received history data from server";
		}
		
		// when firstly joining the market, message received from server
		if(msg.startsWith("first")) {
			String id = msg.replace("first", "").trim();
			if(! context.contains(id)) {
				context.add(id); // add to the end
				context.add(0, id); // add to the first -- holder
			}else {
				if(context.get(0).equals(id)) {
					// if the holder is already this one
				}else {
					context.remove(0); // remove the holder
					context.add(0, id); // add new holder
				}				
			}
			
			return id + " is the first joining trader and has the stock";
		}
		
		// when new trader joining the market
		if(msg.startsWith("join")) {
			String id = msg.replace("join", "").trim();
			if(! context.contains(id)) {
				context.add(id); // add to the end
			}
			
			return id + " joining to the market";
		}
		
		// when trader leaving market
		if(msg.startsWith("leave")) {
			// format, for example: leave 666
			String id = msg.replace("leave", "").trim();
			context.remove(id);
			
			return id + " leaving the market";
		}
		
		// when trader holding the stock leaving market
		if(msg.startsWith("stock")) {
			// format, for example: stock 666 888
			String[] ids = msg.replace("stock", "").trim().split(" ");
			context.remove(0);
			context.remove(ids[0]);
			context.add(0, ids[1]);
			
			return ids[0] + " leaving market and transferring stock to " + ids[1];
		}
		
		// when transaction of the stock
		if(msg.startsWith("transact")) {
			// format, for example: transact from 666 to 999
			String id = msg.split(" ")[4];
			String oldID = context.remove(0);
			context.add(0, id);
			
			return oldID + " transact stock to " + id;
		}
		
		return msg;
	}
	
	// convert the context/background info to a string for printing in the Console
	public static String contextStr(Stock_Client sc) {
		List<String> context = sc.getContext();
		
		if(context.size() == 0)
			return "";
		
		String str = "";
		//str = str + "--------------------------- my id: " + sc.getID();
		
		str = str + "\ncurrent holder of the stock: \n" + context.get(0);
		str = str + "\n" + "current trader(s) in the market: ";
		
		for (int i = 1; i < context.size(); i++) {
			str = str + "\n" + context.get(i);
		}
		
		str = str.replace(sc.getID(), "myself");
		str = "--------------------------- my id: " + sc.getID() + str;
		str = str + "\n" + "---------------------------\n";
		return str;
	}
	
}
