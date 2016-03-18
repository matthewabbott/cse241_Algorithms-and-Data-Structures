//
// INPUT.JAVA
// Input reader for airport and flight data
//
// To read all the information necessary for this lab:
//   (1) Create an object (say, "input") of type Input.
//   (2) Call input.readAirports(<airportFileName>)
//   (3) Call input.readFlights(<flightFileName>)
//
// Note that you *must* do (3) after (2).
//
// If all goes well, you will then have access to
//
//   * input.airports   -- an array of Airport objects
//
//   * input.flights    -- an array of Flight objects
//
//   * input.airportMap -- a HashMap mapping airport codes to the
//                         corresponding Airport objects
//

import java.util.*;

class Input {
    
    //
    // Airport information
    //
    class Airport {
	public String name; // name of airport (3-letter code)
	public int offset;  // offset of local time from GMT (in minutes)
	
	public int id;      // convenient integer identifier
    }
    
    //
    // Flight information
    // NB: all times are GMT, in minutes since midnight
    //
    class Flight {
	public String name; // flight name
	public Airport startAirport, endAirport; // flight termini
	public int startTime, endTime;           // departure and arrival times
    }
    
    //
    // array of all airports read from input
    //
    public Airport airports[];
    
    //
    // array of all flights read from input
    //
    public Flight  flights[];
    
    //
    // mapping from airport codes (strings) to Airport objects
    //
    public HashMap<String,Airport> airportMap;
    
    //////////////////////////////////////////////////////////////////////
    
    // constructor
    public Input()
    {
	airportMap = new HashMap<String,Airport>();
    }
    
    //
    // readAirports()
    // Read the airport file
    //
    public void readAirports(String filename)
    {
	FileParser fp = new FileParser(filename);
	
	// hold the airports as they are read
	ArrayList<Airport> aplist = new ArrayList<Airport>();
	
	while (!fp.isEof())
	    {
		Airport ap = new Airport();
		
		ap.name   = fp.readWord();
		ap.offset = (fp.readInt() / 100) * 60;
		
		if (!fp.isEof())
		    {
			// crete mapping from names to objects
			airportMap.put(ap.name, ap);
			aplist.add(ap);
		    }
	    }
	
	airports = new Airport [aplist.size()];
	aplist.toArray(airports);
    }
    
    //
    // readFlights()
    // read the flight file
    //
    public void readFlights(String filename)
    {
	FileParser fp = new FileParser(filename);
	
	// hold the flights as they are read
	ArrayList<Flight> fllist = new ArrayList<Flight>();
	
	// read the flights and store their times in GMT
	while (!fp.isEof())
	    {
		Flight fl = new Flight();

		String airline;
		int flightno;
		airline = fp.readWord();
		flightno = fp.readInt();
		
		fl.name = airline + "-" + flightno;
		
		if (fp.isEof())
		    break;
		
		String code;
		int tm; 
		String ampm;
		
		code = fp.readWord();
		fl.startAirport = airportMap.get(code);
		
		tm = fp.readInt();
		ampm = fp.readWord();
		
		fl.startTime = toTime(tm, ampm, fl.startAirport.offset);
		
		code = fp.readWord();
		fl.endAirport = airportMap.get(code);
		
		tm = fp.readInt();
		ampm = fp.readWord();
		fl.endTime = toTime(tm, ampm, fl.endAirport.offset);
		
		fllist.add(fl);
	    }
	
	flights = new Flight [fllist.size()];
	fllist.toArray(flights);
    }
    
    
    //
    // toTime()
    // convert raw time value and AM/PM in local time, to minutes
    // since midnight in GMT, using supplied offset from GMT.
    //
    int toTime(int timeRaw, String ampm, int offset)
    {
	int hour   = (timeRaw / 100) % 12;
	int minute = timeRaw % 100;
	
	boolean isPM = (ampm.charAt(0) == 'P');
	
	int minutes = hour * 60 + minute;
	if (isPM) minutes += 12 * 60;
	
	int finalTime = (minutes - offset + 24 * 60) % (24 * 60);
	return finalTime;
    }
}
