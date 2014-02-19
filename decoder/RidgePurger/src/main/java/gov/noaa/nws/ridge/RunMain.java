package gov.noaa.nws.ridge;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jason.Burks
 */
public class RunMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Logger.getLogger(RunMain.class).info("Starting the RidgePurger");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/gov/noaa/nws/ridge/spring/springConfig.xml");
    }

}
