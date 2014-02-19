/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

import gov.noaa.nws.radardecoderlib.colorcurvemanager.BoundedColor;
import gov.noaa.nws.radardecoderlib.colorcurvemanager.BoundedDoubleColor;
import gov.noaa.nws.radardecoderlib.colorcurvemanager.BoundedStringColor;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jdom.Element;

/**
 *
 * @author Jason.Burks
 */
public class LegendFromColorCurveXML {
        Hashtable labelHash = new Hashtable();
        ArrayList<StringLabel> stringLabels = new ArrayList<StringLabel>();
        ArrayList<DoubleLabel> doubleLabels = new ArrayList<DoubleLabel>();
    public LegendFromColorCurveXML(String inputXML, String labels, String outputImage, String title) {
    
        String[] labelTotal = labels.split("\\s+");
        for (int k=0; k< labelTotal.length; ++k) {
            try {
               doubleLabels.add(new DoubleLabel(Double.parseDouble(labelTotal[k])));
            } catch (Exception e) {
                stringLabels.add(new StringLabel(labelTotal[k]));
            }
        }
        try {

            Element root = ProcessXML.openXMLDocument(inputXML).getRootElement();
            List  list = root.getChildren("Level");
            int numColor = list.size();
            BoundedColor[] colors = new BoundedColor[numColor];
            boolean[] labelLegend = new boolean[numColor];
            boolean[] largeLabel = new boolean[numColor];
            for (int i=0; i<numColor; ++i) {
                Element level = (Element)list.get(i);


                 String type = level.getAttributeValue("type");
                 labelLegend[i] = Boolean.parseBoolean(level.getAttributeValue("legendLabel"));
                 largeLabel[i] = Boolean.parseBoolean(level.getAttributeValue("largeLabel"));
                if (type.equals("double")) {
                    double upperValue = Double.MAX_VALUE;
                    double lowerValue = Double.MIN_VALUE;

                    if (level.getAttribute("lowerValue") !=  null) {
                        lowerValue = Double.parseDouble(level.getAttributeValue("lowerValue"));
                    }
                    if (level.getAttribute("upperValue") !=  null) {
                        upperValue = Double.parseDouble(level.getAttributeValue("upperValue"));
                    }
                    if (upperValue != Double.MAX_VALUE && upperValue != Double.MIN_VALUE) {
                         colors[i] = new BoundedDoubleColor(lowerValue,upperValue,ProcessXML.getColor(level));
                    } else if (upperValue != Double.MAX_VALUE && lowerValue == Double.MIN_VALUE) {
                        colors[i] = new BoundedDoubleColor(upperValue,ProcessXML.getColor(level),BoundedDoubleColor.UPPER);

                    } else if (upperValue == Double.MAX_VALUE && lowerValue != Double.MIN_VALUE) {
                        colors[i] = new BoundedDoubleColor(lowerValue,ProcessXML.getColor(level),BoundedDoubleColor.LOWER);
                    }
                    DoubleLabel temp = checkDoubleLabel(lowerValue, upperValue);
                    if (temp != null) {
                         doubleLabels.remove(temp);
                        labelHash.put(colors[i], temp);
                    }

                } else if (type.equals("string")){
                   String name = level.getAttributeValue("value");
                   colors[i] = new BoundedStringColor(name, ProcessXML.getColor(level));
                   StringLabel temp = checkStringLabel(name);
                   stringLabels.remove(temp);
                   if (temp != null) labelHash.put(colors[i], temp);
                }
            }

            Dimension dim = new Dimension(40,375);
            LegendCreator creator = new LegendCreator(colors,labelHash,dim);
            creator.setTitle(title);
            creator.setBarWidth(8);

            ImageIO.write(creator.getImage(), "png", new File(outputImage));
        } catch (IOException ex) {
            System.out.println("Problem");
            Logger.getLogger(LegendFromColorCurveXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StringLabel checkStringLabel(String name) {
        for (int i=0; i< stringLabels.size(); ++i) {
            if (stringLabels.get(i).getString().equals(name)) return stringLabels.get(i);
        }
        return (null);
    }
    public DoubleLabel checkDoubleLabel(double bottomValue, double topValue) {
        for (int i=0; i< doubleLabels.size(); ++i) {
            double value = doubleLabels.get(i).getValue();
            if (value >= bottomValue && value <= topValue) {
                return doubleLabels.get(i);
            }
        }
        return (null);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            String inputFile = args[0];
            String outputFile = args[1];
            String title = args[2];
            System.out.println("Input ="+inputFile+" OutputFile ="+outputFile+" title="+title);
          //  new LegendFromColorCurveXML(inputFile, outputFile, title);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Usage : inputFile outputFile title");
        }
    }

}
