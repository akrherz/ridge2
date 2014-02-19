/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class SymbolData extends RadarData {
    
     double i, j;

     public SymbolData(double i, double j){
         this.i = i;
         this.j = j;
     }
    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

}
