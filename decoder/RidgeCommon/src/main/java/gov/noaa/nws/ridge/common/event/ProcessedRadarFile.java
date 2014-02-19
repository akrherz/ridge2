package gov.noaa.nws.ridge.common.event;


import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Date;

import org.opengis.geometry.DirectPosition;

public class ProcessedRadarFile {
	
   
    
    int siteID = -1;
    int vcp;
    int imageWidth;
    int imageHeight;
    
	private String ZZZ;
    private String XXX;
    byte[] byteImage;
    float elevationAngle;
    Date stormTotalPrecipBegin;
    Date stormTotalPrecipEnd;
    BufferedImage image;
    DirectPosition upperLeft, lowerRight;
    private Date validTime;
    float stormRelativeSpeed = Float.MIN_VALUE;
    float stormRelativeDirection = Float.MIN_VALUE;
   
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public DirectPosition getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(DirectPosition lowerRight) {
        this.lowerRight = lowerRight;
    }

    public DirectPosition getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(DirectPosition upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }
    
    public void setSiteID(int siteID){
        this.siteID = siteID;
    }
    public int getSiteID() {
        return(siteID);
    }
   
   
    public String getZZZ() {
        return ZZZ;
    }

    public void setZZZ(String ZZZ) {
        this.ZZZ = ZZZ;
    }

    
   

    public String getXXX() {
        return XXX;
    }

    public void setXXX(String XXX) {
        this.XXX = XXX;
    }
    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }
    
    public float getElevationAngle() {
		return elevationAngle;
	}

	public void setElevationAngle(float elevationAngle) {
		this.elevationAngle = elevationAngle;
	}

	public Date getStormTotalPrecipBegin() {
		return stormTotalPrecipBegin;
	}

	public void setStormTotalPrecipBegin(Date stormTotalPrecipBegin) {
		this.stormTotalPrecipBegin = stormTotalPrecipBegin;
	}

	public Date getStormTotalPrecipEnd() {
		return stormTotalPrecipEnd;
	}

	public void setStormTotalPrecipEnd(Date stormTotalPrecipEnd) {
		this.stormTotalPrecipEnd = stormTotalPrecipEnd;
	}
	
	public float getStormRelativeSpeed() {
		return stormRelativeSpeed;
	}

	public void setStormRelativeSpeed(float stormrelativespeed) {
		this.stormRelativeSpeed = stormrelativespeed;
	}

	public float getStormRelativeDirection() {
		return stormRelativeDirection;
	}

	public void setStormRelativeDirection(float stormrelativedirection) {
		this.stormRelativeDirection = stormrelativedirection;
	}
	public int getVcp() {
		return vcp;
	}

	public void setVcp(int vcp) {
		this.vcp = vcp;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	@Override
	public String toString() {
		return "ProcessedRadarFile [siteID=" + siteID + ", vcp=" + vcp
				+ ", imageWidth=" + imageWidth + ", imageHeight=" + imageHeight
				+ ", ZZZ=" + ZZZ + ", XXX=" + XXX + ", byteImage="
				+ byteImage + ", elevationAngle="
				+ elevationAngle + ", stormTotalPrecipBegin="
				+ stormTotalPrecipBegin + ", stormTotalPrecipEnd="
				+ stormTotalPrecipEnd + ", image=" + image + ", upperLeft="
				+ upperLeft + ", lowerRight=" + lowerRight + ", validTime="
				+ validTime + ", stormRelativeSpeed=" + stormRelativeSpeed
				+ ", stormRelativeDirection=" + stormRelativeDirection + "]";
	}


}
