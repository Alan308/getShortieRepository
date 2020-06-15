package com.alanco.url.utility;


public class IDGenerator {
	
	private long nano;
	private double rand;
	private long pwr;
	private String s;
	private String sub;
	private int res;

	public int getID() {

        this.nano = System.nanoTime(); 						//Current date time value in nanoseconds.
        this.rand = Math.random() * 1000; 					//Random double.
        this.pwr = (long) (nano * rand); 					//Multiply nano by rand, gives an 18 digit long.

        this.s = pwr + "";									//Convert the result to String.
        this.sub = s.substring(0, 9);						//Extract the first 9 chars.

        this.res = Integer.parseInt(sub);    				//Convert to an integer value.
        
        return res;
    }
}
