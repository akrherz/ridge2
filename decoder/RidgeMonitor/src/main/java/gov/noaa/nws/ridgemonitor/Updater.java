/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgemonitor;

import gov.noaa.nws.ridgemonitor.statistics.RidgeStatus;

/**
 *
 * @author Jason.Burks
 */
public class Updater implements Runnable {
    RidgeMonitor frame;
    RidgeStatus status;
    int updateRate;
    public Updater(RidgeMonitor frame,RidgeStatus status, int updateRate) {
        this.frame = frame;
        this.status = status;
        this.updateRate= updateRate;
    }

    public void run() {
       while(true) {
           updateStats();
            try {
                Thread.sleep(updateRate);
            } catch (InterruptedException ex) {
               // Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }

    private void updateStats() {
       frame.setLastProduct(status.getLastProduct());
       frame.setLastRadar(status.getLastProduct());
       frame.setProcessingRate(status.getProcessingRate());
    }
}
