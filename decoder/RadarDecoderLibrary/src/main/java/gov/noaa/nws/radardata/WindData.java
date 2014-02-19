/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class WindData extends SymbolData {
    int value;
    double speed;
    double direction;

    public WindData(double i, double j, int value, double speed, double direction) {
        super(i,j);
        this.value = value;
        this.speed = speed;
        this.direction = direction;
    }

    public double getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public int getValue() {
        return value;
    }
}
