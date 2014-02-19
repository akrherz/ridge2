package gov.noaa.nws.ridgeserver.chains;

public interface TimeCheck {

	public boolean hasBeenProcessed(long time);
	public void insertTime(long time);
	public void printTimes();
}
