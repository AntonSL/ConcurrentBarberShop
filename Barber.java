package concurrency.barber;

import java.util.Random;

/**
 * A barber.
 * He sleeps if there's no customers in the room or
 * works till the room gets empty
 * @author Anton Lukashchuk
 *
 */
public class Barber implements Runnable {

	private static int counter = 0;
	private int number = counter;
	private WaitingRoom room = null;
	
	public Barber(WaitingRoom room) {
		super();
		this.room = room;
		counter++;
	}

	@Override
	public void run() {
		Customer customerToCut = null;
		System.out.println("Barber #"+this.number+" is here...");
		while(true){
			synchronized (room) {
				while(room.isEmpty()){
					try {
						room.wait();
					} catch (InterruptedException e) {return;}
				}
				System.out.println("Barber #"+this.number+": come in!");
				customerToCut = room.deleteCustomer();
			}
			
			//no need to synch this line as no other barber has ref to this customer
			customerToCut.cut();
			//time to hairCut a customer is between 1.8 and 2.8 seconds;
			int timeRand = new Random().nextInt(1000); 
			try {
				Thread.sleep(1800+timeRand);
			} catch (InterruptedException e) {return;}
			
			System.out.println("Barber #"+this.number+": customer #" + customerToCut.getNumber() + " DONE.");
			
			customerToCut = null;
			synchronized (room) {		
				room.notifyAll();			
			}
		}
		
		
		
	}

}
