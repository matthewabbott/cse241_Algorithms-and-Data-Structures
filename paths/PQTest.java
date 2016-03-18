//
// PQTEST.JAVA
// A unit test for your priority queue class.
//

// Depending on how you implement Handles (in particular, if your
// Handle class is parameterized by the template type T), you may have
// to uncomment the next line to suppress unchecked type warnings.
//
//@SuppressWarnings("unchecked")

class PQTest {
    
    public void runTest(int which)
    {
	switch (which)
	    {
	    case 1:
		System.out.println("*** UNIT TEST ONE ***");
		pqUnitTestOne();
		break;
		
	    case 2:
		System.out.println("*** UNIT TEST TWO ***");
		pqUnitTestTwo();
		break;
		
	    case 3:
		System.out.println("*** UNIT TEST THREE ***");
		pqUnitTestThree();
		break;
		
	    default:
		System.out.println("ERROR: no unit test " + which);
		break;
	    }
    }
    
    // Start by testing just insertion, min, and extraction.
    void pqUnitTestOne()
    {
	PriorityQueue<String> q = new PriorityQueue<String>();
	
	// build up the queue
	q.insert(4, "four");
	q.insert(7, "seven");
	q.insert(1, "one");
	q.insert(6, "six");
	q.insert(12, "twelve");
	q.insert(3, "three");
	q.insert(11, "eleven");
	q.insert(5, "five");
	q.insert(8, "eight");
	q.insert(3, "three-prime");
	q.insert(2, "two");
	
	System.out.println("Initial queue:\n" + q);
	
	// extract the smallest elements from the queue
	System.out.println("Extractions:");
	for (int j = 0; j < 6; j++)
	    {
		int k = q.min();
		String s = q.extractMin();
		
		System.out.println("@" + k + " " + s);
	    }
	
	System.out.println("\nAfter extractions:\n" + q);
	
	// add a few more values
	q.insert(8, "eight-prime");
	q.insert(14, "fourteen");
	q.insert(9, "nine");
	
	System.out.println("After more insertions:\n" + q);
	
	// extract the smallest elements from the queue
	System.out.println("Extractions:");
	while (!q.isEmpty())
	    {
		int k = q.min();
		String s = q.extractMin();
		
		System.out.println("@" + k + " " + s);
	    }
    }
    
    // Make sure Handles are working, then test decreaseKey.
    void pqUnitTestTwo()
    {
	PriorityQueue<String> q = new PriorityQueue<String>();
	Handle handles[] = new Handle [20];
	
	// build up the queue
	handles[9] = q.insert(9, "nine");
	handles[7] = q.insert(7, "seven");
	handles[2] = q.insert(2, "two");	
	handles[8] = q.insert(8, "eight");
	handles[1] = q.insert(1, "one");
	handles[6] = q.insert(6, "six");
	handles[10] = q.insert(10, "ten");
	handles[5] = q.insert(5, "five");
	handles[3] = q.insert(3, "three");
	handles[4] = q.insert(4, "four");
	handles[0] = q.insert(3, "element zero");

	System.out.println("Elements: ");
	// Do the handles point to the correct elements?
	for (int j = 0; j <= 10; j++)
	    {
		System.out.println("@" + 
				   q.handleGetKey(handles[j]) + " " +
				   q.handleGetValue(handles[j]));
	    }
	
	// extract the smallest elements from the queue
	for (int j = 0; j < 5; j++)
	    q.extractMin();
	
	System.out.println("\nAfter extractions: ");
	
	// Do the handles *still* point to the correct elements?
	System.out.println("Elements: ");
	for (int j = 5; j <= 10; j++)
	    {
		System.out.println("@" + 
				   q.handleGetKey(handles[j]) + " " +
				   q.handleGetValue(handles[j]));
	    }
	
	// add some more elements
	handles[12] = q.insert(12, "twelveX");
	handles[15] = q.insert(15, "fifteenX");
	handles[13] = q.insert(13, "thirteenX");
	handles[11] = q.insert(11, "elevenX");
	handles[14] = q.insert(14, "fourteenX");

	// should have no effect
	q.decreaseKey(handles[11], 11);
	q.decreaseKey(handles[11], 15);
	
	// now move some keys around
	q.decreaseKey(handles[12], 1);
	q.decreaseKey(handles[6], 3);
	q.decreaseKey(handles[14], 4);
	q.decreaseKey(handles[9], 2);
	
	// should rearrange the heap and fix up the handles
	System.out.println("Elements: ");
	for (int j = 5; j <= 15; j++)
	    {
		System.out.println("@" + 
				   q.handleGetKey(handles[j]) + " " +
				   q.handleGetValue(handles[j]));
	    }

	// should rearrange the heap and fix up the handles
	System.out.println("Extractions: ");
	while (!q.isEmpty())
	    {
		int k = q.min();
		String s = q.extractMin();
		
		System.out.println("@" + k + " " + s);
	    }
    }
    
    // A lame PRNG that acts the same for both Java and C++
    // Java lacks unsigned types, but integer overflow behaves
    // as expected for 2s-complement arithmetic (i.e. the
    // same as for unsigned ints in C++).  As long as we
    // truncate the final result to 31 bits, we'll get
    // a non-negative integer.
    
    int state = 840287439;
    
    int lcRand() {
	int A = 14357867;
	int B = 23341349;
	
	state = A * state + B;
	
	// return low-order 31 bits
	return (state & ((1 << 31) - 1));
    }
    
    // A little more exercise.
    void pqUnitTestThree()
    {
	PriorityQueue<Integer> q = new PriorityQueue<Integer>();
	Handle handles [] = new Handle [1000];
	
	// Insert a bunch of random elements.
	for (int j = 0; j < 1000; j++)
	    {
		int x = lcRand();
		handles[j] = q.insert(x, new Integer(x));
	    }
	
	// Extract the smallest elements.
	System.out.println("Extractions: ");
	for (int j = 0; j < 50; j++)
	    {
		int k = q.min();
		Integer s = q.extractMin();
		
		System.out.println("@" + k + " " + s);
	    }
	
	System.out.println("************************************");
	
	// Decrease the keys of random elements to random values.
	for (int j = 0; j < 1000; j++)
	    {
		int idx = lcRand() % 1000;
		int k   = lcRand();
		q.decreaseKey(handles[idx], k);
	    }
	
	// Extract the smallest elements.
	System.out.println("Extractions: ");
	for (int j = 0; j < 50; j++)
	    {
		int k = q.min();
		Integer s = q.extractMin();
		
		System.out.println("@" + k + " " + s);
	    }
    }
}
