/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.TextData;
import gov.noaa.nws.radardata.WindData;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public class VADWindRenderer extends RadarSpatialRenderer {
    double xscaling,yscaling;
    Color color = Color.BLACK;
    Color white = Color.WHITE;
    VADWindPathProvider provider;

    public VADWindRenderer(int width, int height) {
        super(width,height,0,0);
        provider = VADWindPathProvider.getInstance();
        xscaling = (width)/(500.);
        yscaling = (height-10)/(500.);
    }

    @Override
    protected void process() {
        super.process();
        int num = data.size();
        for (int i=0; i< num; ++i) {
            RadarData radData = data.get(i);
            if (radData instanceof TextData) {
                TextData event = (TextData)radData;
                drawText(event.getI(),event.getJ(),event.getText());
            } else if (radData instanceof WindData) {
                WindData event = (WindData)radData;
                drawWind(event.getI(),event.getJ(),event.getValue(),event.getSpeed(),event.getDirection());
            }
        }
    }


    public void drawText(double x, double y, String text) {
        //Write the text by the item but offest by 2 scaled pixels
       double xnew= (x)*xscaling;
       double ynew = (y)*yscaling+10.;
       g.setPaint(color);
       g.drawString(text,(float)xnew, (float)ynew);

     }
    public void drawWind(double i,double j, int value, double speed, double direction) {
       double xnew= (i)*xscaling;
       double ynew = (j)*yscaling+10.;
        g.setPaint(colors[value]);
        placeWind(xnew, ynew, direction, speed);
    }

     public void placeWind(double x, double y, double direction, double speed) {
        Shape wind = provider.getWind(direction,speed);
        Rectangle bounds = wind.getBounds();
        AffineTransform moving = AffineTransform.getTranslateInstance(x, y);
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(direction), x, y);
        Shape transformed = rotation.createTransformedShape(moving.createTransformedShape(wind));
        g.draw(transformed);
    }

    @Override
    public void setTransform(MathTransform transform, DirectPosition radarLocation, double elevationAngle) {

    }


}
