//
// PRNG.JAVA
// A simple linear-congruential PRNG, included here so that the
// Java and C++ codes produce the same random numbers.
//

public class PRNG {
    
    public PRNG(long seed) 
    {
	state = ((seed ^ A) & (M - 1));
	for (int j = 0; j < 100; j++)
	    next();
    }
    
    // return a floating-point value in the range [0, 1)
    public double nextDouble()
    {
	long v = next();
	return (double) v / (double) (1L << 32);
    }
    
    ///////////////////////////////////////////////////
    
    private static final long M = (1L << 48);
    private static final long A = 25214903917L;
    private static final long C = 11L;
    
    private long state;
    
    // return an integer in the range [0, 2^32)
    private long next()
    {
	state = (A * state + C) & (M - 1);
	return (state >>> 16);
    }
}
