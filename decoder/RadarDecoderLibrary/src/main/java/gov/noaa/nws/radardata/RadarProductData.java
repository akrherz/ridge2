/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class RadarProductData {
        String defaultColorCurveName;
        double rangeOfProductInMeters;
        double resolutionInMeters;
        String decoderType;
        String rendererType;
        int numberOfLevels;
        int messageCode;

   public RadarProductData(int messageCode,String defaultColorCurveName, double rangeOfProductInMeters, double resolutionInMeters, String decoderType, String rendererType, int numberOfLevels) {
       this.messageCode = messageCode;
       this.defaultColorCurveName = defaultColorCurveName;
        this.rangeOfProductInMeters = rangeOfProductInMeters;
        this.resolutionInMeters = resolutionInMeters;
        this.decoderType = decoderType;
        this.rendererType = rendererType;
        this.numberOfLevels = numberOfLevels;
    }

   public int getMessageCode() {
       return messageCode;
   }

    public String getDecoderType() {
        return decoderType;
    }

    public String getDefaultColorCurveName() {
        return defaultColorCurveName;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public double getRangeOfProductInMeters() {
        return rangeOfProductInMeters;
    }

    public String getRendererType() {
        return rendererType;
    }

    public double getResolutionInMeters() {
        return resolutionInMeters;
    }


}
