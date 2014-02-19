/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.statistics;

import gov.noaa.nws.ridgeserver.events.fileevents.NewFileEvent;
import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;

/**
 *
 * @author Jason.Burks
 */
public class StatisticAdvice implements AfterReturningAdvice {
    private StatisticHolder statsHolder;

    public void setStatsHolder(StatisticHolder statsHolder) {
        this.statsHolder = statsHolder;
    }
    
    public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
        statsHolder.update((NewFileEvent)arg2[0]);
    }

}
