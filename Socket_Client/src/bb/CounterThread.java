package bb;
/*
 * this class from the slides, used to test "synchronized"
 * only for testing
 */
public class CounterThread extends Thread{
	static int TOTAL = 0;
	  static int REPETITIONS = 1000;

	  public CounterThread() {
	    this.start();
	  }
	 
	  public static void inc() {
	    TOTAL++;
	  }

	  @Override
	  public void run() {
	    for (int i = 0; i < REPETITIONS; i++)
	    	inc();
	  }
	  public static void main(String[] args) throws Exception{
		  Thread t1 = new CounterThread();
		    Thread t2 = new CounterThread();
		    t1.join();
		    t2.join();
		    System.out.println("Total = " + TOTAL);

	  }

}
