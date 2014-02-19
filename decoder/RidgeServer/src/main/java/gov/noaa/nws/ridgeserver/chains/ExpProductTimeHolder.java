/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

import gov.noaa.nws.ridgeserver.common.BoundedTreeSet;

/**
 *
 * @author Jason.Burks
 */
public class ExpProductTimeHolder implements TimeCheck{
    BoundedTreeSet<Long> set;
    int number;
    public ExpProductTimeHolder(int numberToHold) {
        this.number = numberToHold;
        set = new BoundedTreeSet<Long>(numberToHold);
        
    }

    public boolean hasBeenProcessed(long time) {
    	//long start = System.nanoTime();
    	synchronized(set) {
        	if (set.contains(time)) {
            	//System.out.println("+ETime Elapsed "+(System.nanoTime()-start));
            	return true;
        	}
        
       // System.out.println("-ETime Elapsed "+(System.nanoTime()-start));
        return(false);
    	}
    }

    public void insertTime(long time) {
    	synchronized(set) {
    		set.add(time);
    	}
    }

   

    public void printTimes() {
    	synchronized(set) {
    	int i=0;
        for (Long timeToWork : set) {
            System.out.println("i="+i+"  time="+timeToWork);
            ++i;
        }
    	}
    }


}
