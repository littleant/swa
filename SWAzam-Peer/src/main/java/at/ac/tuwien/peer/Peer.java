package at.ac.tuwien.peer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Peer implements Runnable {
	final Logger logger = LoggerFactory.getLogger(Peer.class);

	@Override
	public void run() {
		logger.info("Starting peer...");
		
		// run peer code here
		// TODO
	}

	public void stop() {
		logger.info("Stopping peer...");
		
		// shut down peer
		// TODO
		
		logger.info("Peer stopped.");
	}
}
