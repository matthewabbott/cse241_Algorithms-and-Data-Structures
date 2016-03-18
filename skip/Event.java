//
// EVENT.JAVA
//
// Describes a historical event via two public fields:
//  year        -- the year of the event (an integer)
//  description -- the text for the event (a String)
//

class Event {
    
    public int year;             // the year of the event
    public String description;   // the event description
    
    // constructor
    public Event(int iyear, String idescription)
    {
	year = iyear;
	description = idescription;
    }
    
    // print method
    public String toString()
    {
	return String.valueOf(year) + " " + description;
    }

}
