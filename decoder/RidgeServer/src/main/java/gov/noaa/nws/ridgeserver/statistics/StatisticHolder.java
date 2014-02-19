/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.statistics;

import gov.noaa.nws.ridgeserver.events.fileevents.NewFileEvent;
import java.util.ArrayList;

/**
 *
 * @author Jason.Burks
 */
public class StatisticHolder implements RidgeStatus {
    String lastRadar;
    String lastProduct;
    ArrayList<FileStatHolder> toProcess = new ArrayList<FileStatHolder>();
    long avgLatency =0;
    double processingRate=0;

    
    public double getProcessingRate() {
        return processingRate;
    }

    public void setProcessingRate(double processingRate) {
        this.processingRate = processingRate;
    }

    

    
    public void update(NewFileEvent event) {
        lastRadar = event.getXXX();
        lastProduct = event.getZZZ();
        toProcess.add(new FileStatHolder(event));
    }

    
    public String getLastRadar() {
        return(lastRadar);
    }

   
    public String getLastProduct() {
        return(lastProduct);
    }

    public FileStatHolder[] getFiles(){
        FileStatHolder[] holder = new FileStatHolder[toProcess.size()];
        toProcess.toArray(holder);
        toProcess.clear();
        return(holder);
    }

    
    public long getAvgLatency() {
         return avgLatency;
    }
    public void setAvgLatency(long avglatency) {
        this.avgLatency = avglatency;
    }


}
