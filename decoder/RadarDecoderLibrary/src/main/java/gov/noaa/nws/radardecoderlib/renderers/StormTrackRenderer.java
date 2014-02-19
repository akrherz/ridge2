/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.LineData;
import gov.noaa.nws.radardata.MesocycloneData;
import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.StormTrackData;
import gov.noaa.nws.radardata.TextData;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author Jason.Burks
 */
public class StormTrackRenderer extends SymbologyRenderer {
    Color white = Color.WHITE;
    Color color;
    BasicStroke wideStroke = new BasicStroke(1.0f);
    GeneralPath pastPoint = new GeneralPath();
    GeneralPath futurePoint = new GeneralPath();

    public StormTrackRenderer(int width, int height) {
        super(width, height);
        color = Color.BLACK;
        pastPoint.moveTo(0f,0f);
        pastPoint.lineTo(10f,10f);
        pastPoint.moveTo(10f,0f);
        pastPoint.lineTo(0f,10f);

        futurePoint.moveTo(5f,0f);
        futurePoint.lineTo(5f,10f);
        futurePoint.moveTo(0f, 5f);
        futurePoint.lineTo(10f,5f);

    }

    @Override
    protected void process() {
        super.process();
        int num = data.size();
        for (int i=0; i< num; ++i) {
            RadarData radData = data.get(i);
            if (radData instanceof TextData) {
                TextData event = (TextData)radData;
                drawText(event.getI(),event.getJ(),event.getText(),color);
            } else if (radData instanceof StormTrackData){
                 StormTrackData event = (StormTrackData)radData;
                 drawStormPosition(event.getI(),event.getJ(),event.isFuture());
            } else if (radData instanceof MesocycloneData) {
                 MesocycloneData event = (MesocycloneData)radData;
                drawMesocyclone(event.getI(),event.getJ(),event.getRadius());
            }
        }
     }

     public void drawMesocyclone(double x, double y, double radius) {
        try {
            DirectPosition pointone = radarCoord.transformXandYToCRS(new GeneralDirectPosition(x, y));
            double radiusScaled = 2.0;
            double xul = pointone.getOrdinate(0) - radiusScaled;
            double yul = pointone.getOrdinate(1) - radiusScaled;
            double deltaX = radiusScaled * 2;
            g.setPaint(color);
            g.setStroke(wideStroke);
            Arc2D circle = new Arc2D.Double(xul, yul, deltaX, deltaX, 0., 360., Arc2D.OPEN);
            g.draw(circle);
        } catch (Exception ex) {

        }
    }
    public void drawStormPosition(double x, double y, boolean future) {
        try {
            DirectPosition pointone = radarCoord.transformXandYToCRS(new GeneralDirectPosition(x, y));
            double xul = pointone.getOrdinate(0) - 5;
            double yul = pointone.getOrdinate(1) - 5;
            g.setPaint(color);
            g.setStroke(wideStroke);

            if (future) {
                g.draw(futurePoint);
            } else {
                g.draw(pastPoint);
            }
        } catch (Exception ex) {

        }
    }
    public void drawLine(double x1, double y1, double x2, double y2, boolean future) {
        try {
            DirectPosition pointone = radarCoord.transformXandYToCRS(new GeneralDirectPosition(x1, y1));
            DirectPosition pointtwo = radarCoord.transformXandYToCRS(new GeneralDirectPosition(x2, y2));
            if (future) {
                g.setPaint(Color.YELLOW);
            } else {
                g.setPaint(Color.RED);
            }
            g.setStroke(wideStroke);

            Line2D line = new Line2D.Double(pointone.getOrdinate(0), pointone.getOrdinate(1), pointtwo.getOrdinate(0), pointtwo.getOrdinate(1));
            g.draw(line);
        } catch (Exception ex) {

        }
    }

}
