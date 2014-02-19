/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge.liteimagecreator.task;

/**
 *
 * @author Jason.Burks
 */
public class RadarInput {

    private String radarId;
    private String layerId;
    private String imagePath;
    private String bbox;



    public RadarInput(String radarId, String layerId, String imagePath, String bbox) {
        this.radarId = radarId;
        this.layerId = layerId;
        this.imagePath = imagePath;
        this.bbox = bbox;
    }


    public String getBbox() {
        return bbox;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLayerId() {
        return layerId;
    }

    public String getRadarId() {
        return radarId;
    }
}
