/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.postprocessing;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import gov.noaa.nws.ridgeserver.events.fileevents.NewFileEvent;

/**
 *
 * @author Jason.Burks
 */
public interface ProcessRadarFile {
    public void processRadarFile(ProcessedRadarFile radarFile);
}
