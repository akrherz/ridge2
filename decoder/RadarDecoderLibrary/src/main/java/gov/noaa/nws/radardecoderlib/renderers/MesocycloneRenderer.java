/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.MesocycloneData;
import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.TextData;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author Jason.Burks
 */
public class MesocycloneRenderer extends SymbologyRenderer {
     BasicStroke wideStroke = new BasicStroke(2.0f);
    Color color = Color.BLACK;


    public MesocycloneRenderer(int width, int height) {
        super(width, height);
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
            Logger.getLogger(MesocycloneRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            } else if (radData instanceof MesocycloneData) {
                MesocycloneData event = (MesocycloneData)radData;
                drawMesocyclone(event.getI(),event.getJ(),event.getRadius());
            }
        }

      }

}
