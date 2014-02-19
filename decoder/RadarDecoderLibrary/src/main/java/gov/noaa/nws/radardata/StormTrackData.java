/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class StormTrackData extends LineData {
    boolean future;
    public StormTrackData(double i, double j, double i2, double j2, boolean future) {
        super(i, j, i2, j2);
        this.future = future;
    }

    public boolean isFuture() {
        return future;
    }

}
