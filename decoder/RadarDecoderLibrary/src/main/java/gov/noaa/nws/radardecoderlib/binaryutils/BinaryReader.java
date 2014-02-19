/*
 * BinaryReader.java
 *
 * Created on June 3, 2006, 11:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.binaryutils;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author jason.burks
 */
public interface BinaryReader {

    public ByteBuffer getByteBuffer();
    
     public void setByteBuffer(ByteBuffer buffer);
    
    public void setOffsetStartPoint(int value);

    public int getOffsetStartPoint();
    
    public int getShort() throws IOException;
    
    public int getInt() throws IOException;
    
    public float getFloat() throws IOException;
    
    public char getChar() throws IOException;
    
    public int getByteAsInt() throws IOException;
    
    public byte getByte() throws IOException;
    
    public byte[] getBytes(byte[] bytes) throws IOException;
    
    public int[] read4bitInt() throws IOException;
    
    public void seek(int pos) throws IOException;
    
    public void seek(long pos) throws IOException;
    
    public void skip(long pos) throws IOException;
    
    public long getCurrentPosition() throws IOException;
    
    public long getSize() throws IOException;
    
    public void close() throws IOException;
    
}
