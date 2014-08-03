package concurrency.barber;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Room where customers wait for a free barber
 * @author Anton Lukashchuk
 *
 */
public class WaitingRoom {

		private int seats = 0;
		private Queue<Customer> waitingPeople = new LinkedList<>();
				
		public WaitingRoom(int seats) {
			super();
			this.seats = seats;
		}

		public synchronized boolean addCustomer(Customer customer) {
			if(waitingPeople.size()<seats){
				waitingPeople.add(customer);
				System.out.println("Customer #" + customer.getNumber() + " added.");
				System.out.println("People in a queue: "+waitingPeople.size());
				return true;
			} else{
				return false;
			}
		}
		
		public Customer deleteCustomer() {
			Customer toReturn = waitingPeople.poll();
			System.out.println("People in a queue: "+waitingPeople.size());
			return toReturn;
		}
		
		public boolean isEmpty() {
			return waitingPeople.isEmpty();
		}
}
