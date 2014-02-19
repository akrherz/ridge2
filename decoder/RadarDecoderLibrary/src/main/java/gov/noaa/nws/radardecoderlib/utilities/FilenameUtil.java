/*
 * FilenameUtil.java
 *
 * Created on October 10, 2005, 4:04 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author jburks
 */
public class FilenameUtil {
    
    /** Creates a new instance of FilenameUtil */
    public FilenameUtil() {
    }
    public static String[] breakFilename(String filename) {
        String[] file = new String[2];
        
        int indexOfLast = filename.lastIndexOf("/");
        if (indexOfLast ==-1) {
            indexOfLast = filename.lastIndexOf("\\");
        }
        
        file[0] = filename.substring(0, indexOfLast+1);
        file[1] = filename.substring(indexOfLast+1);
        return(file);
    }
    
    public static String getRootFilename(String filename) {
        //Takes filename like temp.dat and returns filename without suffix (temp)
        int indexOfLast = filename.lastIndexOf(".");
        if (indexOfLast >-1) {
            return(filename.substring(0, indexOfLast));
        }
        return(filename);
    }
}
