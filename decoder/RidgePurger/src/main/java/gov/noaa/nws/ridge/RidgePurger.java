package gov.noaa.nws.ridge;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jason.Burks
 */
public class RidgePurger extends QuartzJobBean{
    private RidgeTimeDAO dao;
    private ThreadPoolTaskExecutor executor;
     Date date;
     List<RadarTimeIndex> radars;

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public void setDao(RidgeTimeDAO dao) {
        this.dao = dao;
    }
    

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
         Logger.getLogger(RidgePurger.class).info("Running Purger");
         
        try {
            date = new Date(System.currentTimeMillis() - 7200000);
             Logger.getLogger(RidgePurger.class).info("Getting list of files to process");
            Iterator<RadarTimeIndex> iter = dao.getRadarTimeOlderThan(date).iterator();
            while(iter.hasNext()) {
                executor.execute(new DeleteRadarFile(iter.next(),dao));
            }
            Logger.getLogger(RidgePurger.class).info("Done Processing list");
        } catch (Exception ex) {
            Logger.getLogger(RidgePurger.class.getName()).log(Level.WARN, null, ex);
        }
    }

}
