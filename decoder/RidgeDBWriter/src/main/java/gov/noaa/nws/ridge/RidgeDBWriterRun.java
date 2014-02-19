/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import java.io.PrintStream;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import gov.noaa.nws.ridge.common.logging.*;

/**
 *
 * @author Jason.Burks
 */
public class RidgeDBWriterRun {
final static GeometryFactory factory = new GeometryFactory(new PrecisionModel(),4326);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	System.setErr(new PrintStream(new LoggingOutputStream(Logger.getLogger(RidgeDBWriterRun.class), Level.INFO)));
    	Logger.getLogger(RidgeDBWriterRun.class).info("------Starting Ridge DB Writer--------");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/ridgeDBWriterSpring.xml");
    }

}
