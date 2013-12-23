package at.ac.tuwien.peer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Main.class);
		
		Peer runnable = new Peer();
		Thread thread = new Thread(runnable);
		thread.start();
		
		System.out.println("Press [Enter] to close...");
		try {
			System.in.read();
			
			logger.info("exiting...");
			runnable.stop();
			thread.join();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
