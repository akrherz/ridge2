/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

/**
 *
 * @author jason
 */
public class GeocentricRadiusFinder {
    double a;
//    double f;
//    double R_Squared;
//    double n;
    double eSquared;
    public GeocentricRadiusFinder(double a,double f) {
        this.a = a;
        eSquared = f*(2-f);
//        R_Squared = Math.pow(a, 2);
//        this.f = f;
//        n = 1/Math.pow((1-1/f),2)-1;
        
    }
    
    public double findHeight(double latitude) {
        return(a*(1-eSquared)*Math.pow(1-eSquared*Math.pow(Math.sin(Math.toRadians(latitude)), 2),-3/2));
        //return(Math.sqrt((R_Squared)/(1+n*Math.pow(Math.sin(Math.toRadians(latitude)), 2))));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GeocentricRadiusFinder finder = new GeocentricRadiusFinder(6378137., 1/298.257223563);
        System.out.println("Height for 60.7928 ="+finder.findHeight(60.7928));
    }

}
