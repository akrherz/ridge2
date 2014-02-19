/*
 * DirectoryCleaner.java
 *
 * Created on March 8, 2007, 7:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.utilities;

import java.io.File;

/**
 *
 * @author jason.burks
 */
public class DirectoryCleaner {
    
    /** Creates a new instance of DirectoryCleaner */
    public DirectoryCleaner(String directoryToClean, boolean deleteYes) {
        if (directoryToClean.equals("") != true) {
            System.out.println("Going to be cleaning "+directoryToClean);
             File dir = new File(directoryToClean);
             File[] files = dir.listFiles();
                int numFiles = files.length;
                for (int i=0; i< numFiles; ++i) {
                    if (deleteYes == true) {
                        System.out.println("Deleting "+files[i]);
                        files[i].delete();
                    } else {
                        System.out.println("Would be deleting "+files[i]);
                    }
                }
        }
       
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new DirectoryCleaner("C:/temp/ridge", true);
    }
    
}
