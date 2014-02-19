/*
 * BinaryReaderImpl.java
 *
 * Created on June 3, 2006, 11:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.binaryutils;

/**
 *
 * @author jason.burks
 */
public abstract class BinaryReaderImpl implements BinaryReader {
   int offsetStartPoint=0;
   String file;
    /** Creates a new instance of BinaryReaderImpl */
    public BinaryReaderImpl(String file) {
        this.file = file;
    }
    public void setOffsetStartPoint(int value){
         offsetStartPoint = value;
     }

    public int getOffsetStartPoint() {
        return(offsetStartPoint);
    }
    
}
