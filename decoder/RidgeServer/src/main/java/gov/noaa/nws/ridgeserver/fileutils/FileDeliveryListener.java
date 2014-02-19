/*
 * DirectoryScannerListener.java
 *
 * Created on February 13, 2007, 10:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.fileutils;

import java.io.File;

/**
 *
 * @author jason.burks
 */
public interface FileDeliveryListener {
    public void deliverFile(File file);
    public void deliverFileInBytes(byte[] bytes);
}
