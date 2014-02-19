package gov.noaa.nws.radardecoderlib.radardecoders;

import java.util.Date;

public interface StormTotalPrecipDecoder {
	
	public abstract Date getStormTotalPrecipBegin() throws Exception ;
	
	public abstract Date getStormTotalPrecipEnd() throws Exception ;
}
