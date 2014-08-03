package concurrency.barber;

/**
 * Hairy customer, has ID (instead of name)
 * He enters the room, sits on a free seat if there's one or
 * goes home if all seats are busy
 * @author Anton Lukashchuk
 *
 */
public class Customer implements Runnable{
	
	private static int counter = 0;
	private int id = counter; //id instead of name
	
	private WaitingRoom room = null;
	private boolean hairCut = false;

	public Customer(WaitingRoom room) {
		super();
		this.room = room;
		if(++counter > 1000_000){
			counter=0;
		}
		
	}
	
	@Override
	public void run() {
		
		if (room.addCustomer(this)) {
			synchronized (room) {
				room.notifyAll();
				while(!this.hairCut){
					try {
						room.wait();
					} catch (InterruptedException exitRun) {break;}
				}
			}
		} else{
			System.out.println("Customer #"+id+": No place for me, bye.");
		}
		
	}
	
	public void cut() {
		this.hairCut=true;
	}

	public int getNumber() {
		return id;
	}

}
