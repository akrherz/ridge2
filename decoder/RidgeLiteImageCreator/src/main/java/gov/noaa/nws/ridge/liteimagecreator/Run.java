/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge.liteimagecreator;

import gov.noaa.nws.ridge.liteimagecreator.task.ProcessImageTask;
import gov.noaa.nws.ridge.liteimagecreator.task.RadarInput;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author Jason.Burks
 */
public class Run {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/gov/noaa/nws/ridge/liteimagecreator/springConfig.xml");
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor)ctx.getBean("executor");


        //The following would be generated by JMS Messages coming off the topic. We would hook up and listen and then forward any messages to the
        //executor by creating a RadarInput and a new ProcessImageTask
        RadarInput input1 = new RadarInput("TWX","N0R", "/www/html/ridge2/RadarImg2/N0R/TWX_N0R_1260381793000.png","-99.0103149414062 36.2186851501465 -93.4536819458008 41.7753143310547");
        //RadarInput input2 = new RadarInput("TWX","N1R", "/www/html/ridge2/RadarImg2/N0R/TWX_N1R_1260381793000.png","-99.0103149414062 36.2186851501465 -93.4536819458008 41.7753143310547");

        executor.execute(new ProcessImageTask(input1));
        //executor.execute(new ProcessImageTask(input2));

    }

}
