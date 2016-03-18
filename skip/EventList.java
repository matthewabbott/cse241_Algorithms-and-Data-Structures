//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

class EventList {

	Random randseq;

	private EventNode head;
	private EventNode tail;

	private int maxLvl;

	private final int MINUS_INF = (int) Double.NEGATIVE_INFINITY;
	private final int PLUS_INF = (int) Double.POSITIVE_INFINITY;

	// //////////////////////////////////////////////////////////////////
	// Here's a suitable geometric random number generator for choosing
	// pillar heights. We use Java's ability to generate random booleans
	// to simulate coin flips.
	// //////////////////////////////////////////////////////////////////

	int randomHeight() {
		int v = 1;
		while (randseq.nextBoolean()) {
			v++;
		}
		return v;
	}

	//
	// Constructor
	//
	public EventList() {
		randseq = new Random(58243); // You may seed the PRNG however you like.

		maxLvl = 1;

		head = new EventNode(new Event(MINUS_INF, "head"), maxLvl);
		
		tail = new EventNode(new Event(PLUS_INF, "tail"), maxLvl);
		
			tail.right.add(tail);
			head.right.add(tail);

	}

	//
	// Add an Event to the list.
	//
	public void insert(Event e) {
		int yr = e.year;
		EventNode current = head;
		int height = randomHeight();
		
		extendMaxLevel(height);
		
		EventNode newNode = new EventNode(e, height);

		for (int i = maxLvl; i > 0; i--) {
			EventNode next = current.right.get(i - 1);
			while (next.getEvents().get(0).year < yr) {
				current = next;
				next = current.right.get(i - 1);
			}
			
			if (next.getEvents().get(0).year == yr) {
				
				for(Event storedEvent : next.getEvents()) {
					if(storedEvent.toString().equals(e.toString())){ // check if event e is already stored in the node
						return;
					}
				}
				
				next.getEvents().add(e);
				
				return;
			}
		}
		
		current = head;
		
		for (int i = maxLvl; i > 0; i--) {
			EventNode next = current.right.get(i - 1);
			while (next.getEvents().get(0).year < yr) {
				current = next;
				next = current.right.get(i - 1);
			}
			if (height >= i) {
					newNode.right.add(0, next);
					current.right.set(i - 1, newNode);
			}
		}
	}
	
	public void extendMaxLevel(int height) {
		while (height > maxLvl) {
			for(int i = 0; i < maxLvl; i++) {
				head.right.add(tail);
				tail.right.add(tail);
			}
			
			maxLvl *= 2;
			head.level = maxLvl;
			tail.level = maxLvl;
		}
	}

	//
	// Remove all Events in the list with the specified year.
	//
	public void remove(int year) {
		EventNode current = head;

		for (int i = maxLvl; i > 0; i--) {
			EventNode next = current.right.get(i-1);
			while (next.getEvents().get(0).year < year) {
				current = next;
				next = current.right.get(i - 1);
			}
			
			if (next.getEvents().get(0).year == year) {
				current.right.set(i - 1, next.right.get(i-1));
				
				if (i == 1) {
					deleteNode(next);
				}
			}
		}
	}
	
	private void deleteNode(EventNode toBeDeleted) {
		toBeDeleted.right = null;
		toBeDeleted.setEvents(null);
	}

	//
	// Find all events with greatest year <= input year
	//
	public Event[] findMostRecent(int year) {
		EventNode current = head;

		for (int i = maxLvl; i > 0; i--) {
			EventNode next = current.right.get(i - 1);
			while (next.getEvents().get(0).year <= year) {
				current = next;
				next = current.right.get(i - 1);
			}
		}
		
		if (current == head) {
			return null;
		}
		
		return current.getEvents().toArray(new Event[current.getEvents().size()]);
	}

	//
	// Find all Events within the specific range of years (inclusive).
	//
	public Event[] findRange(int first, int last) {
		EventNode current = head;
		
		for (int i = maxLvl; i > 0; i--) {
			EventNode next = current.right.get(i - 1);
			while (next.getEvents().get(0).year < first) {
				current = next;
				next = current.right.get(i - 1);
			}
		}
		
		EventNode beforeFirstNode = current;
		if (beforeFirstNode.right.get(0) == tail) {
			return null;
		}
		
		current = head;
		
		for (int i = maxLvl; i > 0; i--) {
			EventNode next = current.right.get(i - 1);
			while (next.getEvents().get(0).year <= last) {
				current = next;
				next = current.right.get(i - 1);
			}
		}
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		EventNode lastNode = current;
		
		while(beforeFirstNode != lastNode) {
			current = beforeFirstNode.right.get(0);
			eventList.addAll(current.getEvents());
			
			beforeFirstNode = beforeFirstNode.right.get(0);
		}
		
		return eventList.toArray(new Event[eventList.size()]);
	}
}
