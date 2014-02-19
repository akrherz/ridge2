/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.statistics;

/**
 *
 * @author Jason.Burks
 */
public interface RidgeStatus {
    public String getLastRadar();
    public String getLastProduct();
    public long getAvgLatency();
    public double getProcessingRate();
}
