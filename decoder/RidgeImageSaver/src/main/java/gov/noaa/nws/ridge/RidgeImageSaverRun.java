/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jason.Burks
 */
public class RidgeImageSaverRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    	
    	Logger.getLogger(RidgeImageSaverRun.class).info("------Starting Ridge Image Saver--------");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/ridgeImageSaverSpring.xml");
    }

}
