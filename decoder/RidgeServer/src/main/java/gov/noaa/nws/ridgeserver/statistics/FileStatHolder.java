/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.statistics;

import gov.noaa.nws.ridgeserver.events.fileevents.NewFileEvent;

/**
 *
 * @author Jason.Burks
 */
public class FileStatHolder {
    NewFileEvent event;
    long time =0;
    int latencyInMillis =0;

    public NewFileEvent getEvent() {
        return event;
    }

    public int getLatencyInMillis() {
        return latencyInMillis;
    }

    public long getTime() {
        return time;
    }

    public FileStatHolder(NewFileEvent event) {
        time = System.currentTimeMillis();
        latencyInMillis = (int)(time-event.getValidTime().getTime());
    }


}
