/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class RadialData extends RadarData {

    int[] radialData;
    double startAngle;
    double deltaAngle;

    public RadialData(int[] radialData, double startAngle, double deltaAngle) {
        this.radialData = radialData;
        this.startAngle = startAngle;
        this.deltaAngle = deltaAngle;
    }
    public int[] getData() {
        return(radialData);
    }
    public double getStartingAngle(){
        return(startAngle);
    }
    public double getDeltaAngle() {
        return(deltaAngle);
    }
}
