/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

/**
 *
 * @author Jason.Burks
 */
public class DecodeFilenameException extends Exception {

    /**
     * Creates a new instance of <code>CannotDecodeINputFilenameException</code> without detail message.
     */
    public DecodeFilenameException() {
    }


    /**
     * Constructs an instance of <code>CannotDecodeINputFilenameException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DecodeFilenameException(String msg) {
        super(msg);
    }
}
