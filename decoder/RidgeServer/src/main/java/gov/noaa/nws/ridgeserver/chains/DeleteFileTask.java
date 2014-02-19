package gov.noaa.nws.ridgeserver.chains;

import java.io.File;

import org.apache.log4j.Logger;

public class DeleteFileTask implements Runnable{
	
	File file;
	
	DeleteFileTask(String filename) {
		file = new File(filename);
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			file.delete();
			//System.out.println("Done deleting "+file);
		} catch (Exception e) {
			Logger.getLogger(DecodeTask.class).warn("Problem deleting the file "+file);
		}
		
	}

}
