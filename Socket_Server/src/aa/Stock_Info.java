package aa;
/*
 * this class is for stock
 * it may include many attributes
 * such as interest, coupon, face value, dividend, term and so on 
 * 
 * here just for simplification, only ID and amount attributes are created
 */

public class Stock_Info {

	// id should be 6-digit number
	private String sid = "";

	// every time, at least 10, at most 99, should be transacted
	private int amount = 0;

	private String name = "";
	// private String company = "";

	private String latestHolderID = "";
	private String currentholderID = "";
	
	public Stock_Info() {
		
	}
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatestHolderID() {
		return latestHolderID;
	}

	public void setLatestHolderID(String latestHolderID) {
		this.latestHolderID = latestHolderID;
	}

	public String getCurrentholderID() {
		return currentholderID;
	}

	public void setCurrentholderID(String currentholderID) {
		this.currentholderID = currentholderID;
	}

}
