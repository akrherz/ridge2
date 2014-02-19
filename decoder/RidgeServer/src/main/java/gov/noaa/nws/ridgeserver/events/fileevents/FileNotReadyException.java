package gov.noaa.nws.ridgeserver.events.fileevents;

public class FileNotReadyException extends Exception{

	public FileNotReadyException(String message) {
		super(message);
	}
}
