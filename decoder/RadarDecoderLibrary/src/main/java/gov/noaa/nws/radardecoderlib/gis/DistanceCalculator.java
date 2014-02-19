/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

/**
 *
 * @author jason
 */
public class DistanceCalculator {
    private double sinOfe,cosOfe;
    private double ke =6./5.;
    private double a = 6378000.;
    public DistanceCalculator(float elevationAngle) {
        sinOfe = Math.sin(Math.toRadians(elevationAngle));
        cosOfe = Math.cos(Math.toRadians(elevationAngle));
    }
    public double findGreatCircleDistance(double range) {
        return(ke*a*Math.asin((range*cosOfe)/(ke*a+getHeight(range))));
    }
    
    public double getHeight(double range) {
        return(Math.sqrt(Math.pow(range,2)+Math.pow(ke*a,2)+2*range*ke*a*sinOfe)-ke*a);
    }
    
    public static void main(String[] args) {
        DistanceCalculator distCalc = new DistanceCalculator(0.5f);
        System.out.println("Height = "+distCalc.getHeight(124.*1852.));
        System.out.println("124Nmi  ="+distCalc.findGreatCircleDistance(124.*1852.)/1852.);
    }
}
