/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.TVSData;
import gov.noaa.nws.radardata.TextData;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.GeneralPath;
import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author Jason.Burks
 */
public class TVSRenderer extends SymbologyRenderer {
    BasicStroke wideStroke = new BasicStroke(2.0f);
    GeneralPath tvs = new GeneralPath();
    Color color;
    public TVSRenderer(int width, int height) {
        super(width, height);
        color = Color.RED;
        tvs.moveTo(0f, 0f);
        tvs.lineTo(2.5f,10f);
        tvs.lineTo(5f,0f);
        tvs.lineTo(0f,0f);

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
            } else if (radData instanceof TVSData){
                TVSData event = (TVSData)radData;
                drawTVS(event.getI(),event.getJ());
            }
        }
     }

    public void drawTVS(double x, double y) {
        try {
            DirectPosition pointone = radarCoord.transformXandYToCRS(new GeneralDirectPosition(x, y));
            g.setPaint(color);
            g.setStroke(wideStroke);
            g.translate(pointone.getOrdinate(0) - 2.5, pointone.getOrdinate(1) - 10.);
            g.draw(tvs);
            g.fill(tvs);
        } catch (Exception ex) {
        }
    }

}
