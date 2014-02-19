/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.RadarData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.ArrayList;

/**
 *
 * @author Jason.Burks
 */
public class BaseRenderer {
    int width, height;
    double halfWidth, halfHeight;
    BufferedImage image;
    Graphics2D g;
    Color[] colors;
    ArrayList<RadarData> data;
    public BaseRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        halfHeight = height/2.0;
        halfWidth = width/2.0;
    }

    public void  setColor(Color[] colors) {
        this.colors = colors;
    }

    public void setRadarData(ArrayList<RadarData> data){
        this.data = data;
    }
    protected void process() {
    	byte[] red = new byte[colors.length];
    	byte[] blue = new byte[colors.length];
    	byte[] green = new byte[colors.length];
    	byte[] alpha = new byte[colors.length];
    	for (int i=0; i< colors.length; ++i) {
			if (colors[i] != null) {
				red[i] = (byte) colors[i].getRed();
				blue[i] = (byte) colors[i].getBlue();
				green[i] = (byte) colors[i].getGreen();
				alpha[i] = (byte) colors[i].getAlpha();
			} else {
				red[i] = (byte) 0;
				blue[i] = (byte) 0;
				green[i] = (byte) 0;
				alpha[i] = (byte) 0;
			}
    	}
    	IndexColorModel model = new IndexColorModel(8,colors.length,red,green,blue,alpha);
        image = new BufferedImage(width, height,BufferedImage.TYPE_BYTE_INDEXED,model);
        g = (Graphics2D)image.getGraphics();
       // g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
    }

    public BufferedImage render() {
        process();
        return image;
    }
}
