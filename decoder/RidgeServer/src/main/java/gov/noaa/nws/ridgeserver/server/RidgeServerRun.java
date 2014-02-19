/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.server;

import gov.noaa.nws.ridgeserver.fileutils.TimedDirectoryScanner;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jason.Burks
 */
public class RidgeServerRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	 Logger.getLogger(RidgeServerRun.class).info("------Starting Ridge Server--------");
         ApplicationContext ctx = new ClassPathXmlApplicationContext("ridgeServerSpring.xml");
         ctx.getBean("geoGraphic");
         TimedDirectoryScanner scanner = (TimedDirectoryScanner)ctx.getBean("directoryScanner");
         scanner.startTimer();
    }

}
