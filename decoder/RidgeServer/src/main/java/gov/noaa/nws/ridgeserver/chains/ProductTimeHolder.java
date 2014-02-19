/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

/**
 *
 * @author Jason.Burks
 */
public class ProductTimeHolder implements TimeCheck{
    long[] times ;
    int number;
    public ProductTimeHolder(int numberToHold) {
        this.number = numberToHold;
        times = new long[numberToHold];
        for (int i=0; i< number; ++i) {
            times[i] = 0;
        }
    }

    public boolean hasBeenProcessed(long time) {
       // if (time < times[0]) return true;
    	//long start = System.nanoTime();
        for (Long timeToWork : times) {
        
            if (time == timeToWork) {
            	//System.out.println("+Time Elapsed "+(System.nanoTime()-start));
            	return true;
            }
        }
       // System.out.println("-Time Elapsed "+(System.nanoTime()-start));
        return(false);
    }

    public void insertTime(long time) {
        shiftTimesDown();
        times[0] = time;
    }

    public void shiftTimesDown() {
        for (int i=number-1; i>0; --i) {
            times[i] = times[i-1];
        }
    }

    public void printTimes() {
        for (int i=0; i<number; ++i) {
            System.out.println("i="+i+"  time="+times[i]);
        }
    }


}
