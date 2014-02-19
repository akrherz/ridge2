/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Jason.Burks
 */
public class LogRedirect extends OutputStream {
Logger logger = Logger.getLogger(LogRedirect.class);
private int lineEnd = (int)'\n';
private ByteArrayOutputStream os = new ByteArrayOutputStream();


public void write(int b) throws IOException {
    os.write(b);
    if (b == lineEnd) {
        logger.info(os.toString());
        os.reset();
    }
}
}