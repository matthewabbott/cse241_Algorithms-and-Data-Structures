import java.util.ArrayList;

class EventNode {
    
    private ArrayList<Event> events;
    public int level;
    public ArrayList<EventNode> right;
    
    // constructor
    public EventNode(Event e, int level)
    {
    	this.events = new ArrayList<Event>();
    	this.events.add(e);
    	this.level = level;
    	right = new ArrayList<EventNode>();
    }
   
    public ArrayList<Event> getEvents() {
    	return events;
    }
    
    public void setEvents(ArrayList<Event> newEvents) {
    	events = newEvents;
    }
}
