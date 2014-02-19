/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class LineData extends RadarData {
    private double i,j,i2,j2;
    public LineData(double i, double j, double i2, double j2) {
        this.i = i;
        this.j = j;
        this.i2 = i2;
        this.j2 = j2;
    }

    public double getI() {
        return i;
    }

    public double getI2() {
        return i2;
    }


    public double getJ() {
        return j;
    }

    public double getJ2() {
        return j2;
    }


}
