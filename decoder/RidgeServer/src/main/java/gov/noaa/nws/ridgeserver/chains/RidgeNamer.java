/*
 * RidgeNamer.java
 *
 * Created on July 29, 2006, 7:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom.Element;

/**
 *
 * @author jason.burks
 */
public class RidgeNamer {
    String type;
    String XXX;
    String year;
    String month;
    String day;
    String hour;
    String minute;
    String ZZZ;
    String uuid;
    Hashtable<String,String> table = new Hashtable<String,String>();
    Pattern pat = Pattern.compile(".*(\\w{3})_(\\d{4})(\\d{2})(\\d{2})_(\\d{2})(\\d{2})_(\\w{3}).*");

        TimeZone gmt = TimeZone.getTimeZone("GMT");

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");


    /** Creates a new instance of RidgeNamer */
    public RidgeNamer(Element xml) {
        //Setup basic structure for root directories
        //Need to cycle through definitions and set them up
        yearFormat.setTimeZone(gmt);
         monthFormat.setTimeZone(gmt);
         dayFormat.setTimeZone(gmt);
         hourFormat.setTimeZone(gmt);
         minuteFormat.setTimeZone(gmt); 

       ListIterator listIter =  xml.getChildren("definition").listIterator();
       while(listIter.hasNext()) {
           Element element = (Element)listIter.next();
           addToTable(element.getAttribute("name").getValue(),element.getText());
       }
    }
     public RidgeNamer(String inputFile) throws DecodeFilenameException {
         setupInputFileName(inputFile);
    }
    
    public void setupInputFileName(String inputFileName) throws DecodeFilenameException{
        //do some basic processing to extract pieces
        //XXX_YYYYmmDD_HHMM_ZZZ
        //break out XXX,YYYY,mm,DD,HH,MM, ZZZ
        uuid = UUID.randomUUID().toString();
        Matcher m = pat.matcher(inputFileName);
        if (m.matches()) {
            XXX = m.group(1);
            year = m.group(2);
            month = m.group(3);
            day = m.group(4);
            hour = m.group(5);
            minute = m.group(6);
            ZZZ = m.group(7);
        } else {
            throw(new DecodeFilenameException("Could not Decode filename "+inputFileName));
        }

    }

    public void setDateOfRadarImage(Date date) {
        year = yearFormat.format(date);
        month = monthFormat.format(date);
        day = dayFormat.format(date);
        hour = hourFormat.format(date);
        minute = minuteFormat.format(date);
    }
    public void addToTable(String name,String string) {
        table.put(name,string);
    }
    
    public String getFromTable(String name) {
        return(replace(table.get(name)));
    }
    
     private String replace(String string){
        string = string.replaceAll("XXX",XXX);
        string = string.replaceAll("ZZZ",ZZZ);
        string = string.replaceAll("YEAR",year);
        string = string.replaceAll("MONTH",month);
        string = string.replaceAll("DAY",day);
        string = string.replaceAll("HOUR",hour);
        string = string.replaceAll("MINUTE",minute);
        string = string.replaceAll("UUID",uuid);
        return(string);
    }
     
     public String getXXX() {
         return(XXX);
     }
     
     public String getZZZ() {
         return(ZZZ);
     }
    
//    public String getOutputFileName() {
//       return(outputImageDir+ZZZ+"/"+XXX+"_"+ZZZ+"_0.gif");
//    }
//    public String getRadarSiteOutputFileName() {
//       return(outputImageDir+ZZZ+"/"+XXX+"/"+XXX+"_"+year+month+day+"_"+hour+minute+"_"+ZZZ+".gif");
//    }
//    public String getThumbnailName() {
//       return(thumbnailsDir+"/"+XXX+"_Thumb.gif");
//    }
//    
//    public String getLegendFileName() {
//       return(legendDir+ZZZ+"/"+XXX+"_"+ZZZ+"_Legend_0.gif");
//    }
//    public String getRadarSiteLegendFileName() {
//       return(legendDir+ZZZ+"/"+XXX+"/"+XXX+"_"+year+month+day+"_"+hour+minute+"_"+ZZZ+"_Legend.gif");
//    }
//    
//     public String getProjectedOutputFileName() {
//       return(outputProjectedImageDir+ZZZ+"/"+XXX+"_"+ZZZ+"_0.gif");
//    }
//    public String getProjectedRadarSiteOutputFileName() {
//       return(outputProjectedImageDir+ZZZ+"/"+XXX+"/"+XXX+"_"+year+month+day+"_"+hour+minute+"_"+ZZZ+".gif");
//    }
//    
//    public String getRadarTimeFilename() {
//        return(radarTimeDir+XXX.toLowerCase()+"_"+ZZZ.toLowerCase()+"_time.txt");
//    }
    
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        RidgeNamer namer = new RidgeNamer();
//        try {
//            namer.setupInputFileName("C:/Jason/radardata/HTX_20060729_1435_N0R");
//            namer.addToTable("outputName", "XXX_YEARMONTHDAY_HOURMINUTE_ZZZ");
//            System.out.println("before "+namer.getFromTable("outputName"));
//            namer.setDateOfRadarImage(new Date());
//            System.out.println("after "+namer.getFromTable("outputName"));
////             System.out.println(namer.getRadarSiteOutputFileName());
////             System.out.println("Projected");
////             System.out.println(namer.getProjectedOutputFileName());
////             System.out.println(namer.getProjectedRadarSiteOutputFileName());
////             System.out.println("Legend");
////             System.out.println(namer.getLegendFileName());
////             System.out.println(namer.getRadarSiteLegendFileName());
////             System.out.println("Time");
////             System.out.println(namer.getRadarTimeFilename());
//        } catch (CannotDecodeInputFilenameException ex) {
//            ex.printStackTrace();
//        }
//
//    }
    
}
