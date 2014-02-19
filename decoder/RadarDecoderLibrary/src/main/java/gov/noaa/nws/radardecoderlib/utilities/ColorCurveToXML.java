package gov.noaa.nws.radardecoderlib.utilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import gov.noaa.nws.radardecoderlib.colorcurves.NetCDFColorCurveReader;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason.Burks
 */
public class ColorCurveToXML {
//    String inputFile;
//    String outputFile;
//    float minimumValue;
//    float maximumValue;
//    float increment;
//    int colorCurveNumber;
    int numToSkipLabelling =10;

    public ColorCurveToXML(String inputFile, String outputFile, int colorCurveNumber, String[] wordCategoriesTop, float topValue, float bottomValue, float increment, int numToStartLabel, String[] wordCategoriesBottom, boolean invertColorCurve) {

        System.out.println("Processing " + inputFile);
        System.out.println("Start value =" + topValue + "  Maximum value =" + bottomValue + " increment =" + increment);
        System.out.println("Reading Colorcurve " + colorCurveNumber + " from " + inputFile);

        NetCDFColorCurveReader reader = new NetCDFColorCurveReader(inputFile, colorCurveNumber);
        Color[] colors = reader.getColors();
        if (invertColorCurve) {
            colors = invertColors(colors);
        }
        float amount = topValue;
        int index = 0;
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(outputFile));
//
            out.write("<root>\n");
            for (int j = 0; j < wordCategoriesTop.length; ++j) {
                System.out.println("index=" + index + " Word =" + wordCategoriesTop[j] + "  " + colors[index]);
                if (!wordCategoriesTop[j].equals("")) {
                    out.write("<Level type=\"string\" value=\"" + wordCategoriesTop[j] + "\" legendLabel=\"true\" largeLabel=\"true\">\n");
                    out.write("\t<red>" + colors[index].getRed() + "</red>\n");
                    out.write("\t<green>" + colors[index].getGreen() + "</green>\n");
                    out.write("\t<blue>" + colors[index].getBlue() + "</blue>\n");
                    out.write("\t<alpha>255</alpha>\n");
                    out.write("</Level>\n");
                }
                ++index;
            }
            int amountNumCat = colors.length - wordCategoriesTop.length - wordCategoriesBottom.length;
            boolean label = false;
            int labelCounting = 0;
            String upperValue ="";
            String lowerValue ="";
            for (int i = 0; i < amountNumCat; ++i) {
                if (i >= numToStartLabel) {

                    if (labelCounting == 0) {
                        label = true;
                    } else {
                        label = false;

                    }
                    if (labelCounting == numToSkipLabelling-1) {
                        labelCounting = 0;
                    } else {
                        ++labelCounting;
                    }
                }
                System.out.println("index=" + index + " " + colors[index] + "top =" + amount + "  bottom=" + (amount + increment));
                amount += increment;
                if (i==0) {
                    upperValue = "";
                    lowerValue = " lowerValue=\""+(amount+increment)+"\"";
                } else if (i == (amountNumCat-1)) {
                    upperValue = " upperValue=\""+amount+"\"";
                    lowerValue = "";
                } else {
                    upperValue = " upperValue=\""+amount+"\"";
                    lowerValue = " lowerValue=\""+(amount+increment)+"\"";
                }
                out.write("<Level type=\"double\""+upperValue+lowerValue+" legendLabel=\""+label+"\" largeLabel=\"false\">\n");
                out.write("\t<red>" + colors[index].getRed() + "</red>\n");
                out.write("\t<green>" + colors[index].getGreen() + "</green>\n");
                out.write("\t<blue>" + colors[index].getBlue() + "</blue>\n");
                out.write("\t<alpha>255</alpha>\n");
                out.write("</Level>\n");

                ++index;
            }
            for (int k = 0; k < wordCategoriesBottom.length; ++k) {
                System.out.println("i=" + index + " Word =" + wordCategoriesBottom[k] + "  " + colors[index]);
                if (!wordCategoriesBottom[k].equals("")) {
                    out.write("<Level type=\"string\" value=\"" + wordCategoriesBottom[k] + "\" legendLabel=\"true\" largeLabel=\"true\">\n");
                    out.write("\t<red>" + colors[index].getRed() + "</red>\n");
                    out.write("\t<green>" + colors[index].getGreen() + "</green>\n");
                    out.write("\t<blue>" + colors[index].getBlue() + "</blue>\n");
                    out.write("\t<alpha>255</alpha>\n");
                    out.write("</Level>\n");
                }
                ++index;
            }
            out.write("</root>\n");
//        System.out.println("Outputting "+outputFile);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ColorCurveToXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            String inputFile = args[0];
            String outputFile = args[1];
            int colorCurveNumber = Integer.parseInt(args[2]);
            float minimumValue = Float.parseFloat(args[3]);
            float maximumValue = Float.parseFloat(args[4]);
            float incrementValue = Float.parseFloat(args[5]);
            int numFirstInScale = Integer.parseInt(args[6]);
            int numLastInScale = Integer.parseInt(args[7]);
            int numToStartLabel = Integer.parseInt(args[7]);
            boolean invert = Boolean.parseBoolean(args[8]);
            //new ColorCurveToXML(inputFile, outputFile, colorCurveNumber, minimumValue, maximumValue, incrementValue,numFirstInScale,numLastInScale,numToStartLabel, invert);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Usage : inputFile outputFile colorCurveNumber minimumValue maximumValue increment numFirstInScale numLastInScale invert");
        }

    }

    private void printColors(Color[] colors) {
        int num = colors.length;
        for (int i=0; i< num; ++i) {
            System.out.println("Color "+i+" "+colors[i]);
        }
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
