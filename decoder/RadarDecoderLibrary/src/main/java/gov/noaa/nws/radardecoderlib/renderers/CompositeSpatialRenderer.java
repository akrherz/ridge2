/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.CompositeRowData;
import gov.noaa.nws.radardecoderlib.gis.CompositeCoordinateToCRS;
import java.awt.geom.GeneralPath;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public class CompositeSpatialRenderer extends RadarSpatialRenderer {
    CompositeCoordinateToCRS radarCoord; 
    GeneralPath path = new GeneralPath();

    public CompositeSpatialRenderer(int width, int height, double binWidth, double productRange) {
        super(width, height, binWidth, productRange);
    }

    private void drawBin(int i) {
        try {
            DirectPosition[] pos = radarCoord.getPoints(i);
            path.reset();
            path.moveTo(pos[0].getOrdinate(0), pos[0].getOrdinate(1));
            path.lineTo(pos[1].getOrdinate(0), pos[1].getOrdinate(1));
            path.lineTo(pos[2].getOrdinate(0), pos[2].getOrdinate(1));
            path.lineTo(pos[3].getOrdinate(0), pos[3].getOrdinate(1));
            path.lineTo(pos[0].getOrdinate(0), pos[0].getOrdinate(1));
            g.fill(path);
        } catch (Exception ex) {

        }
    }

    @Override
     protected void process() {
        long timeStart = System.currentTimeMillis();
        super.process();
        int numBins = data.size();
        radarCoord.setPixelsPerRow(numBins);
             for (int j=0; j < numBins; ++j) {
                 CompositeRowData rowEvent = (CompositeRowData)data.get(j);
                 radarCoord.setPixelRow(j);
                 int[] value = rowEvent.getData();
              //   totalItemsPossible = totalItemsPossible+value.length;
                 for (int i=0; i< value.length; ++i) {
                     if (colors[value[i]] != null) {
                         g.setColor(colors[value[i]]);
                         drawBin(i);
//                         ++totalItemsDrawn;
                     }
                 }
             }
            // System.out.println("Time to render="+(System.currentTimeMillis()-timeStart));

    } 
    
    @Override
    public void setTransform(MathTransform transform, DirectPosition radarLocation, double elevationAngle) {
        radarCoord = new CompositeCoordinateToCRS(transform,radarLocation.getOrdinate(0),radarLocation.getOrdinate(1), binWidth, productRange);
        radarCoord.intialize();
    }


}
