/*
 * BinaryUtilities.java
 *
 * Created on June 3, 2006, 11:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.binaryutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipInputStream;

import ucar.nc2.dt.radial.Nids2Dataset;
import ucar.unidata.io.bzip2.CBZip2InputStream;

/**
 * 
 * @author jason.burks
 */
public class BinaryUtilities {

	public static final String headerEnd = new String(new byte[] { (byte) 13,
			(byte) 13, (byte) 10 });

	/** Creates a new instance of BinaryUtilities */
	public BinaryUtilities() {
	}
	
	public static boolean[] bitsFromBytes(byte[] bytes) {
		int length = bytes.length*8;
		 int bitIndex = 0;
		 int arrayIndex = 0;

		boolean[] output = new boolean[length];
		for (int i=0; i<length; ++i) {
			output[i] = (bytes[arrayIndex] >> (7 - bitIndex) & 1) == 1;
			bitIndex++;
	        if(bitIndex == 8)
	        {
	            bitIndex = 0;
	            arrayIndex++;
	        }

		}
		
		return output;
	}

	public static BitSet fromByteToBitSet(byte[] bytes) {
		
		BitSet bits = new BitSet(bytes.length*8);

		for (int i = 0; i < bytes.length * 8; i++) {
			System.out.println("Working on "+i);
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
				//bits.set(i);
				bits.set(i, true);
			} else {
				bits.set(i, false);
			}
		}
		return bits;
	}

	public static BitSet fromByteToBitSet(byte bytes) {
		BitSet bits = new BitSet();

		for (int i = 0; i < 8; i++) {
			if ((bytes & (1 << (i % 8))) > 0) {
				bits.set(i);
			}
		}
		return bits;
	}

	public static void setupHeaderOffset(BinaryReader binread) {
		// This is to take into account the header on some radar files. Some
		// have a 30 byte header, and some 40, so I am just take a 50 byte
		// sample and look for the last place it has two carriage returns.

		try {
			// Get snippet of data to check for the header.
			binread.seek(0);
			byte[] bytes = new byte[120];

			binread.getBytes(bytes);
			String headerpart = new String(bytes);
			// Now find if the header contains ^M^M and find the last one. Then
			// if found add 3 to it and start there, else rewind the buffer and
			// start at 0 becuase it has no header.
			int value = headerpart.lastIndexOf(headerEnd);

			int start = 0;
			if (value != -1) {
				start = value + 3;
				// Becuase the file contains some WMO header I need to clip that
				// from the product based on the end of the header found above.
				byte[] oldarray = binread.getByteBuffer().array();
				byte[] array = new byte[oldarray.length - start];
				System.arraycopy(oldarray, start, array, 0, array.length);
				binread.setByteBuffer(ByteBuffer.wrap(array));
			} else {
				// rewind binread if no header found.
				binread.seek(0);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static BinaryReader unCompress(BinaryReader binaryReader)
			throws IOException {
		try {
			ByteBuffer buf = binaryReader.getByteBuffer();
			binaryReader.close();
			int uncompressedSize = buf.getInt(102);
			ByteBuffer outputBuf = ByteBuffer.allocate(uncompressedSize + 120);
			byte[] header = new byte[120];
			buf.position(0);
			buf.get(header);
			outputBuf.position(0);
			outputBuf.put(header);
			byte[] output = new byte[buf.limit() - 120];
			buf.position(120);
			buf.get(output, 0, output.length);

			ByteArrayInputStream bais = new ByteArrayInputStream(output);
			try {
				bais.read();
				bais.read();
				CBZip2InputStream bzStream = new CBZip2InputStream(bais);
				byte[] arrayUn = new byte[uncompressedSize];
				bzStream.read(arrayUn);
				outputBuf.position(120);
				outputBuf.put(arrayUn, 0, uncompressedSize);
				return new MappedByteBufferReader(outputBuf,
						binaryReader.getOffsetStartPoint());
			} catch (IOException ex) {
				Logger.getLogger(BinaryUtilities.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		} catch (IOException ex) {
			Logger.getLogger(BinaryUtilities.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		throw new IOException("Problem reading compressed file");
	}

	public static boolean isBZip2Compressed(BinaryReader reader)
			throws IOException {
		try {
			reader.seek(120);
			String string = new String(new byte[] { reader.getByte(),
					reader.getByte() });

			if (string.equals("BZ"))
				return true;
		} catch (IOException ex) {
			throw new IOException("Problem reading compressed file");
		}
		return false;
	}
	
	public static boolean isReady(BinaryReader reader) {
		try {
			if (reader.getSize() >0 ) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	// Need to look for the last instance of \r\r\n which marks the end of the
	// header.
	public static int findEndOfHeader(BinaryReader reader) {
		try {
			reader.seek(0);
			// grab the first 120 bytes
			byte[] bytes = new byte[120];
			reader.getBytes(bytes);
			// convert to string then search for last instance of that string of
			// \r\r\n
			String headerpart = new String(bytes);
			// Now find if the header contains \r\r\n and find the last one.
			// Then
			// if found add 3 to it and start there, else rewind the buffer and
			// start at 0 because it has no header.
			int value = headerpart.lastIndexOf(headerEnd);
			// add 3 to account for \r\r\n to truly put you at the end of the
			// header.
			int endOfHeader = value + 3;
			return endOfHeader;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	// This method was adapted netcdf library
	public static boolean isZlibCompressed(BinaryReader reader) {
		try {
			// Jump to the end of the header and read two bytes in and do some
			// conversion and check versus expected values.
			int endOfHeader = findEndOfHeader(reader);
			reader.seek(endOfHeader);
			short b0 = convertUnsignedByteToShort(reader.getByte());
			short b1 = convertUnsignedByteToShort(reader.getByte());
			if ((b0 & 0xf) == (byte) 8) {
				if ((b0 >> 4) + 8 <= (byte) 15) {
					if ((((b0 << 8) + b1) % 31) == 0) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static BinaryReader zlibUncompress(BinaryReader reader) throws IOException {
		int size = (int) reader.getSize();
	    int endOfHeader = findEndOfHeader(reader);
		reader.seek(0);
		byte[] compressedBytes  = new byte[size];
		reader.getBytes(compressedBytes);
		Nidsheader nidsHeader = new Nidsheader();
		byte [] realoutput = nidsHeader.GetZlibedNexr(compressedBytes,size,endOfHeader);
		
		 reader.setByteBuffer(ByteBuffer.wrap(realoutput));
		return reader;
		
//		int size;
//		try {
//			System.out.println("zlib uncompress ");
//			size = (int) reader.getSize();
//			int endOfHeader = findEndOfHeader(reader);
//			reader.seek(endOfHeader);
//			byte[] compressedBytes  = new byte[size-endOfHeader];
//			reader.getBytes(compressedBytes);
//			// Create a ByteArrayOutputStream to push the uncompressed data
//			// into.
//			ByteArrayOutputStream output = new ByteArrayOutputStream();
//			// Create the inflater
//			Inflater inflater = new Inflater(false);
//			// put the uncompressed data into the inflater for use.
//			inflater.setInput(compressedBytes);
//			// Create a buffer to pull the data out
//			byte[] buffer = new byte[1024];
//			int resultLength = 0;
//			// now start working on getting the data
//			while (inflater.getRemaining() != 0) {
//				try {
//					resultLength = inflater.inflate(buffer);
//					output.write(buffer, 0, resultLength);
//				} catch (DataFormatException ex) {
//					break;
//				}
//				// if I get result Length 0 then I need to reset it to read
//				// more.
//				if (resultLength == 0) {
//					int remaining = inflater.getRemaining();
//					System.out.println("Remaining "+remaining);
//					if (remaining != 4) {
//						inflater.reset();
//						inflater.setInput(compressedBytes,
//								compressedBytes.length - remaining, remaining);
//					} else {
//						break;
//					}
//				}
//
//			}
//			System.out.println(output.toByteArray().length);
//			 reader.setByteBuffer(ByteBuffer.wrap(output.toByteArray()));
//			return reader;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw e;
//		}
	}

	public static short convertUnsignedByteToShort(byte b) {
		return (short) ((b < 0) ? (short) b + 256 : (short) b);
	}

	// public static void main(String[] args) {
	// MappedByteBufferReader reader;
	// try {
	// reader = new MappedByteBufferReader(
	// "/Users/Jason.Burks/Data/Radar/latest_radar/N0Q/N0Q_20101208_1715");
	// BinaryUtilities.setupHeaderOffset(reader);
	//
	// System.out.println("got message code =" + reader.getShort());
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// //
	// MappedByteBufferReader reader2;
	// try {
	// reader2 = new MappedByteBufferReader(
	// "/Users/jason.burks/Data/Radar/Ridge/ComparisonTestData/orig/FWS_20030406_0156_N0R");
	//
	// BinaryUtilities.setupHeaderOffset(reader2);
	//
	// reader2.seek(0);
	// System.out.println("2 got message code =" + reader2.getShort());
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// MappedByteBufferReader reader3;
	// try {
	// reader3 = new MappedByteBufferReader(
	// "/Users/Jason.Burks/Data/Radar/07Jan2009Radarkhtx.tar/2009Jan07/radar/khtx/Z/elev0_5/res1/level16/20090107_0002");
	// BinaryUtilities.setupHeaderOffset(reader3);
	//
	// System.out.println("3 got message code =" + reader3.getShort());
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public static byte[] toByteArray(BitSet bits) {
		byte[] bytes = new byte[bits.length() / 8 + 1];
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
			}
		}
		return bytes;
	}

}
