/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.RadialData;
import gov.noaa.nws.radardecoderlib.gis.RadialCoordinateToCRS;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public class RadialSpatialRenderer extends RadarSpatialRenderer {
    RadialCoordinateToCRS radarCoord;
     BasicStroke wideStroke = new BasicStroke(1.0f);
     double strokewidth;
     GeneralPath path = new GeneralPath();

    public RadialSpatialRenderer(int width, int height, double binWidth, double productRange) {
        super(width, height, binWidth, productRange);
    }



    public void setTransform(MathTransform transform,DirectPosition radarLocation,  double elevationAngle) {
        radarCoord = new RadialCoordinateToCRS(transform,radarLocation.getOrdinate(0),radarLocation.getOrdinate(1),(float)elevationAngle,binWidth);
        radarCoord.intialize();
    }

    private void drawBin(int j) {
        try {
          if (j != 0) {
            DirectPosition[] pos = radarCoord.getPoints(j);
            path.reset();
            path.moveTo(pos[0].getOrdinate(0), pos[0].getOrdinate(1));
            path.lineTo(pos[1].getOrdinate(0), pos[1].getOrdinate(1));
            path.lineTo(pos[2].getOrdinate(0), pos[2].getOrdinate(1));
            path.lineTo(pos[3].getOrdinate(0), pos[3].getOrdinate(1));
            path.lineTo(pos[0].getOrdinate(0), pos[0].getOrdinate(1));
            g.fill(path);
            }
        } catch (Exception ex) {
            
        }


    }

    @Override
    protected void process() {
        super.process();
        long timeStart = System.currentTimeMillis();
        int numRadials = data.size();
             for (int i=0; i < numRadials; ++i) {
                 RadialData radialData = (RadialData)data.get(i);
                 double deltaAngle = radialData.getDeltaAngle();
                 double startAngle = radialData.getStartingAngle();
                 int[] value = radialData.getData();
                 radarCoord.setRadialAngle(startAngle, deltaAngle);
                 //totalItemsPossible = totalItemsPossible+value.length;
                 for (int j=0; j< value.length; ++j) {
                     if (colors[value[j]] != null) {
                         g.setColor(colors[value[j]]);
                         drawBin(j);
//                         ++totalItemsDrawn;
                     }
                 }
             }
            // System.out.println("Time to render="+(System.currentTimeMillis()-timeStart));
    }


}
