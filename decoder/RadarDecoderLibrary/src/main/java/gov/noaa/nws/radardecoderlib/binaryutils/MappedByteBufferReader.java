/*
 * MappedByteBufferReader.java
 *
 * Created on June 3, 2006, 4:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.binaryutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author jason.burks
 */
public class MappedByteBufferReader extends BinaryReaderImpl{
    ByteBuffer mbb;
//    int offsetStartPoint=0;
    /** Creates a new instance of MappedByteBufferReader */
    public MappedByteBufferReader(String file) throws FileNotFoundException, IOException {
        super(file);
            FileInputStream fs = new FileInputStream(file);
            //Get the file channel from the input stream
            FileChannel fc = fs.getChannel();
            //pull in whole file to a memory map;
            mbb = ByteBuffer.allocate((int)fc.size());
            fc.read(mbb);
            
            fc.close();
            fs.close();
            mbb.rewind();
         
    }
     public MappedByteBufferReader(byte[] array) throws FileNotFoundException, IOException {
        super("");
            mbb = ByteBuffer.wrap(array);
            mbb.rewind();
    }
     public MappedByteBufferReader(ByteBuffer buf, int startPoint) throws FileNotFoundException, IOException {
        super("");
        this.setOffsetStartPoint(startPoint);
            mbb = buf;
            mbb.rewind();
    }

    @Override
   public void setOffsetStartPoint(int value){
         offsetStartPoint = value;
     }
    
    //need to implement getString(length),get
    public int getShort() throws IOException{
        return(mbb.getShort());
    }
    
    public int getInt() throws IOException{
        return(mbb.getInt());
    }
    
    public float getFloat() throws IOException{
        return(mbb.getFloat());
    }
    
    public char getChar() throws IOException{
        return((char)mbb.get());
    }
    
    public int[] read4bitInt() throws IOException{
        int[] ints = new int[2];
        int value =0;
        value |= mbb.get() & 0xFF;
        ints[0] = value & 0x0f; // lower 4 bits
        ints[1] = (value >> 4) & 0x0f; //upper 4 bits
        return(ints);
    }
    
    public void seek(int pos) throws IOException {
        mbb.position(pos+offsetStartPoint);
    }
     public void seek(long pos) throws IOException{
        mbb.position((int)pos+offsetStartPoint);
    }
     
     public void skip(long pos) throws IOException{
         mbb.position((int)pos+offsetStartPoint+mbb.position());
     }
    
    public long getCurrentPosition() throws IOException {
        return(mbb.position());
    }
    
    public long getSize() throws IOException {
        return(mbb.capacity());
    }

    public byte getByte() throws IOException {
       return(mbb.get());
    }

    public int getByteAsInt() throws IOException {
        int i = 0;
        i |= mbb.get() & 0xFF;
         return(i);
    }

    public void close() throws IOException {
        //do nothing because closed before
        mbb.clear();
        mbb = null;
    }

    public ByteBuffer getByteBuffer() {
        return mbb;
    }

    public void setByteBuffer(ByteBuffer buffer) {
       mbb = buffer;
       mbb.rewind();
    }
    
	public byte[] getBytes(byte[] bytes) throws IOException {
		// TODO Auto-generated method stub
		mbb.get(bytes);
		return bytes;
	}
    
}
