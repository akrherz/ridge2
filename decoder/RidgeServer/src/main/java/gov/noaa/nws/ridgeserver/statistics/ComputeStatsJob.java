/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.statistics;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Jason.Burks
 */
public class ComputeStatsJob extends QuartzJobBean {
    StatisticHolder holder;
    int amount;
    FileStatHolder[] files;
    int dt;

    public void setHolder(StatisticHolder holder) {
        this.holder = holder;
    }

    public void setDt(int dt) {
        this.dt = dt/1000;
    }

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException  {
            files =  holder.getFiles();
            amount = files.length;
            computeLatency();
            computeProcessingRate();
    }

    public void computeLatency() {
         long total = 0;
         for (int i=0; i< amount; ++i) {
             total += files[i].getLatencyInMillis();
         }
         holder.setAvgLatency((int)(total/(double)amount));
    }

    private void computeProcessingRate() {
        holder.setProcessingRate(amount/(double)dt);

    }
}
