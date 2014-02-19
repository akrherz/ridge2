package gov.noaa.nws.ridge.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class RidgeImageSaverTest extends TestCase {

	public void testRadarDateTimeFormat() {
		Date date = new Date();
		//YYYYMMDDHHMI
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		System.out.println("Date ="+dateFormat.format(date));
	}
}
