package gov.noaa.nws.ridgeserver.events.fileevents;

public class FileAlreadyProcessedException extends Exception {

	public FileAlreadyProcessedException(String message) {
		super(message);
	}
}
