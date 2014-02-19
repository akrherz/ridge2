/*
 * ColorCurveManager.java
 *
 * Created on July 30, 2006, 1:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurvemanager;

import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.DoubleThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.StringThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import gov.noaa.nws.radardecoderlib.utilities.ProcessXML;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import org.jdom.DataConversionException;
import org.jdom.Element;

/**
 *
 * @author jason.burks
 */
public class ColorCurveManager {
    BoundedColor[] colors;
    /** Creates a new instance of ColorCurveManager */
    public ColorCurveManager(String name) throws IOException {
         Element root = ProcessXML.openXMLDocument(getClass().getResource(name)).getRootElement();
            setupColors(root);
    }
    public ColorCurveManager(File file) throws IOException {
        Element root = ProcessXML.openXMLDocument(new FileInputStream(file)).getRootElement();
            setupColors(root);
    }
    
    
    public void setupColors(Element xml) {
        List list = xml.getChildren("Level");
        int numColors = list.size();
        ListIterator listIter = list.listIterator();
        colors = new BoundedColor[numColors];
        int count=0;
         while (listIter.hasNext()) {
            Element element = (Element)listIter.next();
            try {
                Color color= ProcessXML.getColor(element);
                double lowerValue = -1*Double.MAX_VALUE;
                double upperValue = Double.MAX_VALUE;
                if (element.getAttribute("type").getValue().equals("double")) {
                    if (element.getAttribute("lowerValue") != null) {
                        lowerValue = element.getAttribute("lowerValue").getDoubleValue();
                    }
                    if (element.getAttribute("upperValue") != null) {
                        upperValue = element.getAttribute("upperValue").getDoubleValue();
                    }
                    colors[count] = new BoundedDoubleColor(lowerValue, upperValue,color);
                } else if (element.getAttribute("type").getValue().equals("string")){
                    colors[count] = new BoundedStringColor(element.getAttribute("value").getValue(),color);
                }
                ++count;
            } catch (DataConversionException ex) {
                ex.printStackTrace();
            }
         }
        
    }
    
    public Color[] getColors(Threshold[] thresholds) {
        int num = thresholds.length;
        int numLevels = colors.length;
        Color[] outputColors = new Color[num];
        for (int i=0; i<num; ++i) {
            boolean colorFound = false;
            if (thresholds[i] instanceof DoubleThreshold) {
                DoubleThreshold threshold = (DoubleThreshold)thresholds[i];
                for (int j=0; j< numLevels; ++j) {
                    if (colors[j] instanceof BoundedDoubleColor) {
                        if (((BoundedDoubleColor)colors[j]).isMatch(threshold.getValue())){
                        	//System.out.println("Found "+colors[j].toString()+"  threshold="+threshold.toString());
                            outputColors[i] = colors[j].getColor();
                            colorFound = true;
                            break;
                        }
                    }
                }
                
                
            } else if (thresholds[i] instanceof StringThreshold) {
                StringThreshold threshold = (StringThreshold)thresholds[i];
                for (int j=0; j< numLevels; ++j) {
                    if (colors[j] instanceof BoundedStringColor) {
                        if (((BoundedStringColor)colors[j]).isMatch(threshold.getString())){
                        	//System.out.println("Found "+colors[j].toString()+"  threshold="+threshold.toString());
                            outputColors[i] = colors[j].getColor();
                            colorFound = true;
                            break;
                        }
                    }
                }
            }
            if (colorFound == false) {
                outputColors[i] = null;
            }
        }
            
           
        //if setup then return old thresholds
        return(outputColors);
    }
}
