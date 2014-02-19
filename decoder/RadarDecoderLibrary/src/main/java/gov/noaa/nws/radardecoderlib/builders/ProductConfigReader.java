/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.builders;

import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.utilities.ProcessXML;
import org.jdom.Element;

/**
 *
 * @author Jason.Burks
 */
public class ProductConfigReader {
    private static ProductConfigReader reader;


    private ProductConfigReader(){

    }

    public static ProductConfigReader getInstance() {
        if (reader == null) {
            reader = new ProductConfigReader();
        } 
        return(reader);
    }

    public  RadarProductData getProductData(int messageCode) throws Exception {
        Element product = ProcessXML.openXMLDocument(getClass().getResource("/product/" + String.valueOf(messageCode) + ".xml")).getRootElement();
        String defaultColorCurve = product.getChild("colorcurve").getText();
        Element rangeOfProductElement = product.getChild("rangeofproduct");
        double rangeOfProductInMeters = convertToMeters(Double.parseDouble(rangeOfProductElement.getText()), rangeOfProductElement.getAttributeValue("units"));

        Element resOfProductElement = product.getChild("resolution");
        double resOfProductInMeters = convertToMeters(Double.parseDouble(resOfProductElement.getText()), resOfProductElement.getAttributeValue("units"));

        String decoderType = product.getChild("decodertype").getText();
        String rendererType = product.getChild("renderertype").getText();
        int dataLevels = Integer.parseInt(product.getChild("numDataLevels").getText());

        return (new RadarProductData(messageCode, defaultColorCurve, rangeOfProductInMeters, resOfProductInMeters, decoderType, rendererType, dataLevels));
    }


    public double convertToMeters(double value, String units) throws Exception {
        if (units.equals("Km")) {
            return(value*1000.);
        } else if (units.equals("m")) {
            return(value);
        } else if (units.equals("Nmi")) {
            return(value*1852.);
        }
        throw new Exception("Units not found");
    }
}
