/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.builders.ProductConfigReader;
import gov.noaa.nws.radardecoderlib.colorcurvemanager.ColorCurveManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jason
 */
public class RadarProductType {
 /*<property name="name" value="N0R"/>
        <property name="messageCode" value="19"/>
        <property name="colorCurve"  value="ReflectivityColorCurveManager"/>
        <property name="range" value="130" />
        <property name="imageWidth" value="1000"/>
        <property name="imageHeight" value="1000"/>*/
    int messageCode;
    String colorCurve;
    ColorCurveManager colorManager;
    double rangeInMeters;
    int imageWidth;
    int imageHeight;

    public ColorCurveManager getColorManager() {
        return colorManager;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public RadarProductData getRadarData() {
        return radarData;
    }

    public double getRangeInMeters() {
        return rangeInMeters;
    }
    RadarProductData radarData;
    public RadarProductType(int messageCode,String colorCurve,double rangeInMeters, int imageWidth,int imageHeight) {
        this.messageCode = messageCode;
        try {
                radarData = ProductConfigReader.getInstance().getProductData(messageCode);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(RadarProductType.class.getName()).log(Level.SEVERE, null, ex);
            }
        this.colorCurve = colorCurve;
        try {
            colorManager = new ColorCurveManager("/colorcurves/" + colorCurve + ".xml");
        } catch (IOException ex) {
            Logger.getLogger(RadarProductType.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.rangeInMeters = rangeInMeters;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }
}
