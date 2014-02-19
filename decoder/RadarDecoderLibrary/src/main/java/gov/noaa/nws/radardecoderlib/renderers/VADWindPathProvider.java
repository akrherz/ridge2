/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 *
 * @author Jason.Burks
 */
public class VADWindPathProvider {

    private static VADWindPathProvider vadprovider;
    double sLength = 28;
    double bLength = 9;
    GeneralPath winds[] = new GeneralPath[61];


    private VADWindPathProvider() {

    }


    public static VADWindPathProvider getInstance() {
        if (vadprovider == null) {
            vadprovider = new VADWindPathProvider();
        }
        return(vadprovider);
    }

    public Shape getWind(double direction,double speed) {
        int s = (int) Math.round(speed / 5.0);
        if (winds[s] == null) {
            buildWinds(s);
        }
        return winds[s];
    }

       private void buildWinds(int s) {
            int speed = s*5;
            GeneralPath wind = new GeneralPath();
            wind.moveTo(0,0);
            //main stem
            wind.lineTo(0,-sLength);

            if (speed > 2 && speed < 8) {
                Point2D p = wind.getCurrentPoint();
                wind.moveTo(p.getX(),p.getY()+3);
                wind = draw5(wind);
            } else {
                while (speed > 0) {
                    if (speed >= 48) {
                        //System.out.println("Speed: " + speed);
                        wind = draw50(wind);
                        speed = speed-50;
                    } else if (speed >= 8) {
                        //System.out.println("Speed: " + speed);
                        wind = draw10(wind);
                        speed = speed -10;
                    } else if (speed >= 3) {
                        wind = draw5(wind);
                        speed = 0;
                    } else {
                        speed = 0;
                    }
                }
            }
            winds[s] = wind;
    }
    private GeneralPath draw50(GeneralPath p) {
        Point2D start = p.getCurrentPoint();
        double startI = start.getX();
        double startJ = start.getY();
        double endI = startI + bLength;
        double endJ = startJ;
        p.lineTo(endI, endJ);
        endI = startI;
        endJ = startJ + 4;
        p.lineTo(endI, endJ);
        endJ = endJ + 1;
        p.moveTo(endI, endJ);
        return p;
    }

    private GeneralPath draw10(GeneralPath p) {
        Point2D start = p.getCurrentPoint();
        double startI = start.getX();
        double startJ = start.getY();
        double endI = startI + bLength;
        double endJ = startJ;
        p.lineTo(endI, endJ);
        endI = startI;
        endJ = endJ + 3;
        p.moveTo(endI, endJ);
        return p;
    }

    private GeneralPath draw5(GeneralPath p) {
        Point2D start = p.getCurrentPoint();
        double startI = start.getX();
        double startJ = start.getY();
        double endI = startI + 1 + bLength/2;
        double endJ = startJ;
        p.lineTo(endI, endJ);
        return p;
    }


}
