package at.ac.tuwien.swa.swazam.peer;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class to start the SWAzam peer
 * 
 * @author Andreas
 */
public class Main {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Main.class);

		PropertyConfigurator.configure("log4j.properties");

		Peer runnable = new Peer("localhost", 8888, 12345L);
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
