package aa;

/*
 * this class is used to parse and encapsulate the messages sent from client(s)
 * because the data sent from client(s) are formatted
 * for example:
 * when new trader joining: join id
 * when trader leaving: leave id
 * when transacting: transact id newHolderId
 */

public class Message {
	
	public String msg = "";
	
	public Message() {
		
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String m) {
		msg = m;
	}
	
	// categorize the message as which types
	// join, leave, or transact
	public int categorize() {
		if(msg.contains("join")) {
			return 1;
		}
		
		if(msg.contains("leave")) {
			return 2;
		}
		
		if(msg.contains("transact")) {
			return 3;
		}
		
		return -1;
	}
	
	// when joining and leaving, the message format is: join/leave id
	// this method is to get the id from the message
	public String parseID() {
		String id = "";
		if(categorize() == 1) {
			id = (msg.split(" "))[0];
		}else if(categorize() == 2) {
			id = (msg.split(" "))[0];
		}
		
		return id;
	}
	
	// when transacting, the message format is: transact id newHolderId
	public String[] parseTransact() {
		String[] info = null;
		if(categorize() == 3) {
			info = msg.split(" ");
		}
		return info;
	}
	
}	
