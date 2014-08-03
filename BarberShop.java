package concurrency.barber;

/**
 * A barber shop, has barbers, room and clients
 * @author Anton Lukashchuk
 *
 */
public class BarberShop {

	private int barbersToHire = 0;
	private int roomCapacity = 0;
	private int clientComeDelayMs = 100;

	public BarberShop(int barbersToHire, int roomCapacity, int clientComeDelayMs) {
		super();
		this.barbersToHire = barbersToHire;
		this.roomCapacity = roomCapacity;
		this.clientComeDelayMs = clientComeDelayMs;
		
		open();
	}

	/**
	 * open the shop
	 */
	private void open() {
		//open a room
		WaitingRoom room = new WaitingRoom(roomCapacity);
		//hire barbers
		for (int barbers = 0; barbers < this.barbersToHire; barbers++) {
			new Thread(new Barber(room)).start();
		}
		//advertise clients...
		while(true){
			new Thread(new Customer(room)).start();
			try {
				Thread.sleep(this.clientComeDelayMs);
			} catch (InterruptedException exit) {
				break;
			}
		}
		
	}
	
}
