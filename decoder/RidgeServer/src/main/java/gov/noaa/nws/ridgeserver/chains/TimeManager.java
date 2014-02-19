/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

import java.util.Hashtable;

/**
 *
 * @author Jason.Burks
 */
public class TimeManager {
    Hashtable<String,TimeCheck> radars = new Hashtable<String,TimeCheck>();
    int numberTimes;
    public TimeManager(int numberTimes) {
        this.numberTimes = numberTimes;
    }
   

    public TimeCheck getProductTimeHolder(String name) {
    	//System.out.println("Product holder "+name);
        if (radars.get(name) == null) {
            TimeCheck holder = new ExpProductTimeHolder(numberTimes);
            radars.put(name,holder);
            return(holder);
        } else {
          return(radars.get(name));
        }
    }

}
