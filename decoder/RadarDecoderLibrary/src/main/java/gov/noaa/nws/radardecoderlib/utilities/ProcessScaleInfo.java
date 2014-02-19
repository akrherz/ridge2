/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

import gov.noaa.nws.radardecoderlib.colorcurves.NetCDFColorCurveReader;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jason
 */
public class ProcessScaleInfo {
    ArrayList<Relation> relations = new ArrayList<Relation>();
    public ProcessScaleInfo(String inputColorCurve, int colorCurve, String scale, String outputfile, boolean invertColorCurve) {
        try {
            NetCDFColorCurveReader reader = new NetCDFColorCurveReader(inputColorCurve, colorCurve);
            Color[] colors = reader.getColors();
            //if (invertColorCurve) colors = invertColors(colors);
            String[] strings = scale.split(",");
            for (int i = 0; i < strings.length - 1; ++i) {
                String[] parts = strings[i].split(":");
                if (parts[1].matches("[A-Z]+")) {
                    StringRelation sRelation = new StringRelation(Double.parseDouble(parts[0]), parts[1]);
                    relations.add(sRelation);
                } else {
                    String[] partValue = strings[i + 1].split(":");
                    
                        LinearRelation relation = new LinearRelation(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(partValue[0]), Double.parseDouble(partValue[1]));
                        relations.add(relation);
                   
                }
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(outputfile));
//
            out.write("<root>\n");
            for (int j = 0; j <= 255; ++j) {
                int i;
                if (invertColorCurve) {
                   i = 255-j;
                } else {
                    i=j;
                }
                Relation rel = findRelation(i);
                if (rel != null) {
                    if (rel instanceof StringRelation) {
                        out.write("<Level type=\"string\" value=\"" + ((StringRelation)rel).getName() + "\" largeLabel=\"true\">\n");
                        out.write("\t<red>" + colors[i].getRed() + "</red>\n");
                        out.write("\t<green>" + colors[i].getGreen() + "</green>\n");
                        out.write("\t<blue>" + colors[i].getBlue() + "</blue>\n");
                        out.write("\t<alpha>255</alpha>\n");
                        out.write("</Level>\n");
                    } else {
                        boolean leaveOffLower = false;
                        boolean leaveOffUpper = false;
                        Relation ahead = findRelation(i+1);
                        Relation behind = findRelation(i-1);
                        if (behind == null || behind instanceof StringRelation) leaveOffLower = true;
                        if (ahead == null || ahead instanceof StringRelation) leaveOffUpper = true;
                        if (leaveOffUpper) {
                            out.write("<Level type=\"double\" lowerValue=\"" + ((LinearRelation)rel).getValue(i) + "\">\n");
                        } else if ( leaveOffLower) {
                            out.write("<Level type=\"double\" upperValue=\"" + ((LinearRelation)ahead).getValue(i+1) + "\">\n");
                        } else {
                            out.write("<Level type=\"double\" lowerValue=\"" + ((LinearRelation)rel).getValue(i) + "\" upperValue=\"" + ((LinearRelation)ahead).getValue(i+1)+ "\">\n");
                        }
                        out.write("\t<red>" + colors[i].getRed() + "</red>\n");
                        out.write("\t<green>" + colors[i].getGreen() + "</green>\n");
                        out.write("\t<blue>" + colors[i].getBlue() + "</blue>\n");
                        out.write("\t<alpha>255</alpha>\n");
                        out.write("</Level>\n");
                    }
                }
            }
            out.write("</root>\n");
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ProcessScaleInfo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public Relation findRelation(int value) {
        int size = relations.size();
        for (int i=0; i< size; ++i) {
           if (relations.get(i).isContained(value)) {
               return (relations.get(i));
           }
        }
        return(null);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
     //new ProcessScaleInfo("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc",104,"16:-32.5,33:-20,240:75,255:94.5","C:/temp/refoutputcolor.xml",true);
         new ProcessScaleInfo("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc",105,"1:RF,2:-247,26:-100,232:100,255:245","C:/temp/veloutputcolor.xml",true);
         new ProcessScaleInfo("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc",106,"6:0.0,16:0.1,32:0.3,48:0.6,64:1.0,80:1.5,96:2.0,112:2.5,128:3.0,144:4.0,160:5.0,176:6.0,192:8.0,208:10.0,224:12.0,240:15.0,255:31.0","C:/temp/stpoutputcolor.xml",true);
    }
private Color[] invertColors(Color[] colors) {
       int num = colors.length;
       Color[] output = new Color[num];
       for (int i=0; i< num; ++i) {
           output[i] = colors[num-i-1];
       }
       return(output);
    }
}
