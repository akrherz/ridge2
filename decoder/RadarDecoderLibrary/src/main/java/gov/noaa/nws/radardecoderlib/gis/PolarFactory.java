/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.awt.Rectangle;

import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.geometry.GeneralPosition;
import org.geotools.referencing.CRS;
import org.geotools.referencing.operation.builder.GridToEnvelopeMapper;
import org.geotools.api.geometry.Position;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.datum.PixelInCell;
import org.geotools.api.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public class PolarFactory {

    public static CoordinateHolder getTransformForPolar(int width, int height, double lat, double lon, double distance) throws Exception {
        GridEnvelope2D range = new GridEnvelope2D(new Rectangle(width, height));
        CoordinateReferenceSystem crs = getCRSfromLatLon(lat,lon);
        ReferencedEnvelope envel = new ReferencedEnvelope(-distance, distance, -distance, distance, crs);
        GridToEnvelopeMapper mapper = new GridToEnvelopeMapper();
        mapper.setReverseAxis(new boolean[]{false, true});
        mapper.setSwapXY(false);
        mapper.setPixelAnchor(PixelInCell.CELL_CORNER);
        mapper.setEnvelope(envel);
        mapper.setGridRange(range);
        Position pos = new GeneralPosition(0,0);
        Position pos1 = new GeneralPosition(width,height);

        mapper.createTransform().inverse().transform(pos,pos);
        mapper.createTransform().inverse().transform(pos1,pos1);
        return new CoordinateHolder(mapper.createTransform().inverse(), pos, pos1);
    }

     public static CoordinateReferenceSystem getCRSfromLatLon(double lat, double lon) throws Exception {
        GeocentricRadiusFinder finder = new GeocentricRadiusFinder(6378137., 1./298.257223563);
        double radius = finder.findHeight(lat);
        CoordinateReferenceSystem wgs =  CRS.decode("EPSG:4326");
        CoordinateReferenceSystem crs2 = CRS.parseWKT("GEOGCS[\"WGS84_2\",   DATUM[\"WGS84_2\",     SPHEROID[\"WGS84\", " + radius+ ", 0.],TOWGS84[0,0,0,0,0,0,0]],   PRIMEM[\"Greenwich\", 0.0],   UNIT[\"degree\", 0.017453292519943295],   AXIS[\"Geodetic longitude\", EAST],   AXIS[\"Geodetic latitude\", NORTH]]");
        MathTransform trans = CRS.findMathTransform(wgs, crs2);
        GeneralPosition pos = new GeneralPosition(lon,lat);
        GeneralPosition pos2 = new GeneralPosition(0, 0);
        trans.transform(pos, pos2);
        return (CRS.parseWKT("PROJCS[\"Orthographic\"," + "  GEOGCS[\"Orthographic\"," + "DATUM[\"WGS_1984\",SPHEROID[\"WGS_1984\", " + radius + ", 0],TOWGS84[0,0,0,0,0,0,0]], PRIMEM[\"Greenwich\", 0],  UNIT[\"Decimal_Degree\", 0.017453292519943295]],  PROJECTION[\"Orthographic\"],   PARAMETER[\"Central_Meridian\"," + pos2.getOrdinate(0) + "],   PARAMETER[\"Latitude_of_Origin\", " + pos2.getOrdinate(1)+ "],  UNIT[\"Meter\", 1] ]"));

    }

}
