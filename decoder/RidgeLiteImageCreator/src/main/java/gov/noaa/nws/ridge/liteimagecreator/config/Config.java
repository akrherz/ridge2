/*
 * Reads ridge2lite.properties file to get configuration information
 */

package gov.noaa.nws.ridge.liteimagecreator.config;
import gov.noaa.nws.ridge.liteimagecreator.utility.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton to provide access to the configuration
 * @author brian.walawender
 */
public class Config {
     private static Config config;
     private String temporaryPath;
     private String wwwImagePath;
     private String templateFile;
     private String template;



    private Config () {
           

        try {
            Properties properties = new Properties();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ridge2lite.properties");
            properties.load(inputStream);

            temporaryPath = properties.getProperty("temporaryPath");
            wwwImagePath = properties.getProperty("wwwImagePath");
            templateFile = properties.getProperty("templateFile");
            try {
                template = FileReader.readTemplate(templateFile);
            } catch (Exception ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Could not open the template file need to exit the program");
                System.exit(1);

            }
            System.out.println("properties temporaryPath="+temporaryPath);
            System.out.println("properties imagePath="+wwwImagePath);
            System.out.println("properties templatePath="+templateFile);
        } catch (IOException e) {
            // Bummer
            System.out.println("Cannot find ridge2lite.properties file");
            System.exit(1);
        }
    }

    public static  Config getInstanceConfig() {
        // Read properties file.
        if (config == null) {
            config = new Config();
        }
        return(config);

     }


    public String getTemplateFile() {
        return templateFile;
    }

    public String getTemporaryPath() {
        return temporaryPath;
    }

    public String getWwwImagePath() {
        return wwwImagePath;
    }

    public String getTemplate() {
        return template;
    }
}
