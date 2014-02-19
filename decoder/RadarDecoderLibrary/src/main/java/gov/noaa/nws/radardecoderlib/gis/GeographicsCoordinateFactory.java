/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geotools.coverage.grid.GeneralGridEnvelope;
import org.geotools.coverage.grid.GeneralGridGeometry;
import org.geotools.geometry.Envelope2D;
import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.referencing.operation.builder.GridToEnvelopeMapper;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.datum.PixelInCell;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author jason
 */
public class GeographicsCoordinateFactory {

    public static final CoordinateReferenceSystem wgs84 = DefaultGeographicCRS.WGS84;

    public static CoordinateHolder getTransformForGeo(int width, int height, DirectPosition upperLeft, DirectPosition lowerRight) throws Exception {
        CoordinateReferenceSystem crs = getUTMCRSfromLatLon();
        GeneralGridEnvelope range = new GeneralGridEnvelope(new Rectangle(width, height));
        Envelope2D envel = new Envelope2D(wgs84, upperLeft.getOrdinate(0), lowerRight.getOrdinate(1), lowerRight.getOrdinate(0) - upperLeft.getOrdinate(0), upperLeft.getOrdinate(1) - lowerRight.getOrdinate(1));
        GeneralGridGeometry gridGeom = new GeneralGridGeometry(range, envel);
        GridToEnvelopeMapper mapper = new GridToEnvelopeMapper();
        mapper.setReverseAxis(new boolean[]{false, true});
        mapper.setSwapXY(false);
        mapper.setGridType(PixelInCell.CELL_CORNER);
        mapper.setEnvelope(envel);
        mapper.setGridRange(range);
        return new CoordinateHolder(mapper.createTransform().inverse(), upperLeft, lowerRight);
    }

    public static CoordinateHolder getTransformForGeo(int width, int height, DirectPosition center, double distance) throws Exception {
        CoordinateReferenceSystem crs = getUTMCRSfromLatLon();
        GeodeticCalculator calc = new GeodeticCalculator();
        calc.setStartingGeographicPoint(center.getOrdinate(0), center.getOrdinate(1));
        calc.setDirection(-90.0, distance);
        Point2D pointLeft = calc.getDestinationGeographicPoint();
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;
        double deltaX = center.getOrdinate(0) - pointLeft.getX();
        double deltaY = (deltaX / halfWidth) * halfHeight;
        DirectPosition upperLeft = new GeneralDirectPosition(center.getOrdinate(0) - deltaX, center.getOrdinate(1) + deltaY);
        DirectPosition lowerRight = new GeneralDirectPosition(center.getOrdinate(0) + deltaX, center.getOrdinate(1) - deltaY);
      //  System.out.println("UpperLeft ="+upperLeft+"  lowerRight ="+lowerRight);
        return getTransformForGeo(width, height, upperLeft, lowerRight);
    }

    public static CoordinateReferenceSystem getUTMCRSfromLatLon() throws Exception {

        return (GeographicSingleton.getInstance().getGeographicCoordinate());

    }

    public static void main(String[] args) {
        try {

            DirectPosition center = new GeneralDirectPosition(-87.0, 34.0);
            MathTransform transform = GeographicsCoordinateFactory.getTransformForGeo(600, 550, center, 124 * 1852.).getTransform();
            DirectPosition newTest = new GeneralDirectPosition(-86.0, 35.0);
            DirectPosition output = new GeneralDirectPosition(0, 0);
            transform.transform(newTest, output);
           // System.out.println("Output =" + output);
        } catch (Exception ex) {
            Logger.getLogger(GeographicsCoordinateFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
