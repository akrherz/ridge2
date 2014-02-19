/*
 * DirectoryScanner.java
 *
 * Created on February 13, 2007, 10:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.fileutils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Timer;
import org.apache.log4j.Logger;

/**
 *
 * @author jason.burks
 */
public class TimedDirectoryScanner {
    FileDeliveryListener listener;
    Timer timer;
    int updateInterval;
    File dir;
    int numListeners=0;
    FilenameFilter filter;
    File[] files;
    
    /** Creates a new instance of DirectoryScanner */
    public TimedDirectoryScanner(String directory, int updateInterval) {
        Logger.getLogger(TimedDirectoryScanner.class).info("Watching "+directory+"  every "+updateInterval);
        this.updateInterval = updateInterval;
        dir = new File(directory);
        filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            if (name.endsWith(".part") || name.endsWith(".ridge")) {
                return(false);
            }
            return true;
        }
    };
        //startTimer();
    }
    public void setListener(FileDeliveryListener listener) {
        this.listener=listener;
    }
    
    public synchronized void sendMessage(File file) {
            listener.deliverFile(file);
       
    }
    public void startTimer() {
        stopTimer();
        timer = new java.util.Timer();
        class Checker extends java.util.TimerTask {
            //TimerTask is something that's run every time the timer requests it
            public void run() {
                files = dir.listFiles(filter);
                for (File file: files) {
                        sendMessage(file);
            
                }
            }
        }
        //the timer runs a new instance of ZoneCheck() as defined above every 3 hours
        timer.schedule(new Checker(),0,updateInterval);
    }
    /**
     * Stops the thread from updating the skew time.
     */
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
    
    
}
