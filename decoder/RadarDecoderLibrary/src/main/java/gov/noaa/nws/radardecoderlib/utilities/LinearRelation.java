/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author jason
 */
public class LinearRelation implements Relation {
    double m;
    double b;
    double byteTop;
    double byteBottom;

    public LinearRelation(double byteBottom, double valueBottom, double byteTop, double valueTop) {
       
        this.byteTop = byteTop;
        this.byteBottom = byteBottom;
        m = (valueTop-valueBottom)/(byteTop-byteBottom);
        b = valueTop-m*byteTop;
    }

    public boolean isContained(double byteValue){
        if (byteValue >= byteBottom && byteValue <= byteTop) {
            return(true);
        } else {
            return(false);
        }

    }

    public double getValue(double byteValue) {
        return(m*byteValue+b);
    }

    @Override
    public String toString() {
        return("Relation: Top: "+byteTop+"  Bottom: "+byteBottom);
    }

}
