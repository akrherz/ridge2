package gov.noaa.nws.ridgeserver.chains;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class DeleteSingleton {
	
	private ThreadPoolTaskExecutor executor;
	private static DeleteSingleton singleton;
	
	private DeleteSingleton() {
		
	}
	
	public static DeleteSingleton getInstance() {
		if (singleton == null) singleton = new DeleteSingleton();
		return singleton;
	}
	
	public void setExecutor(ThreadPoolTaskExecutor executor) {
		this.executor = executor;
	}
	
	public void deleteFile(String file) {
		executor.execute(new DeleteFileTask(file));
	}
	
}
