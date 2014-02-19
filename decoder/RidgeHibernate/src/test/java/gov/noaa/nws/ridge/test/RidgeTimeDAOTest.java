package gov.noaa.nws.ridge.test;

import java.util.Date;
import java.util.List;

import gov.noaa.nws.ridge.RadarTimeIndex;
import gov.noaa.nws.ridge.RidgeTimeDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;


@ContextConfiguration(locations={"/hibernateConfig.xml"})
public class RidgeTimeDAOTest extends AbstractTransactionalJUnit38SpringContextTests {

	@Autowired
	@Qualifier("radarDAO")
    RidgeTimeDAO ridgeTimeDAO;

	public void testSaveRadarTime() {
		RadarTimeIndex index = getRadarTimeIndex(0);
		ridgeTimeDAO.saveRadarTime(index);
	}
	
	public void testSaveRadarTimeWithVCP() {
		RadarTimeIndex index = getRadarTimeIndex(0);
		index.setVcp(31);
		ridgeTimeDAO.saveRadarTime(index);
	}
	
	public void testSaveRadarTimeWithSRM() {
		RadarTimeIndex index = getRadarTimeIndex(0);
		index.setSrmDirection(270.f);
		index.setSrmSpeedKts(15.f);
		ridgeTimeDAO.saveRadarTime(index);
	}
	
	public void testSaveRadarTimeWithStormTotalPrecip() {
		RadarTimeIndex index = getRadarTimeIndex(0);
		index.setStpEndDateTime(new Date());
		index.setStpStartDateTime(new Date());
		ridgeTimeDAO.saveRadarTime(index);
	}
	
	public void testSaveRadarTimeWithAll() {
		RadarTimeIndex index = getRadarTimeIndex(0);
		index.setStpEndDateTime(new Date());
		index.setStpStartDateTime(new Date());
		index.setSrmDirection(270.f);
		index.setSrmSpeedKts(15.f);
		index.setVcp(31);
		ridgeTimeDAO.saveRadarTime(index);
	}

	public void testGetRadarTimeOlderThan() {
		Date date = new Date();
		RadarTimeIndex index = getRadarTimeIndex(86400*1000*2);
		ridgeTimeDAO.saveRadarTime(index);
		try {
			List<RadarTimeIndex> olderItems = ridgeTimeDAO.getRadarTimeOlderThan(date);
			if (olderItems.size() <= 0) {
				fail("Did not find older entry");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Did not find older items");
		}
		
	}

	public void testDeleteRadarTime() {
		RadarTimeIndex index = getRadarTimeIndex(0);
		ridgeTimeDAO.saveRadarTime(index);
		ridgeTimeDAO.deleteRadarTime(index);
		
	}
	
	
	public Date getOlderDate(long diffdate) {
		Date date = new Date();
		date.setTime(date.getTime()-diffdate);
		return date;
	}
	
	public Geometry createGeom() {
		 GeometryFactory factory = new GeometryFactory(new PrecisionModel(),4326);
		 Coordinate[] coords = new Coordinate[5];
         coords[0] = new Coordinate(-90.0,40.0);
         coords[1] = new Coordinate(-80.0,40.0);
         coords[2] = new Coordinate(-80.0,30.0);
         coords[3] = new Coordinate(-90.0,30.0);
         coords[4] = new Coordinate(-90.0,40.0);
         return(factory.createPolygon(factory.createLinearRing(coords),null));
	}
	
	public RadarTimeIndex getRadarTimeIndex(long datediff) {
		RadarTimeIndex index = new RadarTimeIndex();
		index.setDatetime(getOlderDate(datediff));
		index.setLayer("N0R");
		index.setRadar("HTX");
		index.setRadarPath("/www2/html");
		Geometry geom = createGeom();
		index.setTheGeom(geom);
		return index;
	}

}
