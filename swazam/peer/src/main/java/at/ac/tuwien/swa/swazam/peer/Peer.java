package at.ac.tuwien.swa.swazam.peer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.swa.swazam.peer.music.MusicLibrary;

/**
 * P2P peer that fingerprints music files and connects to other peers to receive music-identification-requests
 * 
 * @author Andreas
 */
public class Peer implements Runnable {
	final Logger logger = LoggerFactory.getLogger(Peer.class);

	@Override
	public void run() {
		logger.info("Starting peer...");
		
		// fingerprint some files and save them in the database
		MusicLibrary musicLibrary = new MusicLibrary();
		musicLibrary.fingerprintDisk();
		
		// run peer code here
		// TODO
		
		logger.info("Peer stopped.");
	}

	/**
	 * Stop the execution of this runnable
	 */
	public void stop() {
		logger.info("Stopping peer...");
		
		// shutdown peer
		// TODO
	}
}
