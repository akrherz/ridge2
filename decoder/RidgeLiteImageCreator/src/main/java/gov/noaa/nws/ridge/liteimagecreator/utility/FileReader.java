/*
 * Reads in the mapserver template file used to make a radar specific
 * temporary mapfile
 */

package gov.noaa.nws.ridge.liteimagecreator.utility;
import java.io.*;

/**
 *
 * @author brian.walawender
 */
public class FileReader {
    //Only one template

    //Static method
 
    public static String readTemplate (String templateFile) throws Exception{
            FileInputStream file = new FileInputStream (templateFile);
            byte[] b = new byte[file.available ()];
            file.read(b);
            file.close ();
            return(new String (b));
    }
}
