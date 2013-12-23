package at.ac.tuwien.peer;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Peer implements Runnable {
	final Logger logger = LoggerFactory.getLogger(Peer.class);

	@Override
	public void run() {
		logger.info("Starting peer...");
		
		// fingerprint some files and save them in the database
		String audioFilePath = "/home/anty/Uni/VU_Software_Architecture/github/swa/music/";
		File[] files = new File[1];
		File file = new File(audioFilePath);
		files[0] = file;
		
		logger.info("Fingerprinting \"{}\"...", audioFilePath);
		int fingerprintCount = this.fingerprintFiles(files);
		logger.info("{} fingerprints generated.", fingerprintCount);
		
		// run peer code here
		// TODO
		
		logger.info("Peer stopped.");
	}
	
	/**
	 * Fingerprint all audio files of the directories and return number of fingerprinted files.
	 * 
	 * @param files Files or directories to fingerprint
	 * @return Number of files that have been fingerprinted
	 */
	private int fingerprintFiles(File[] files) {
		int fileCount = 0;
		
		logger.debug("Got {} files!", files.length);
		
		for (File file : files) {
			if (file.isDirectory()) {
	            logger.debug("Directory: {}", file.getName());
	            fileCount += this.fingerprintFiles(file.listFiles());
	        } else {
	            logger.debug("File: {}", file.getName());
	            
	            // fingerprint file
	            // TODO

	            fileCount++;
	        }
		}
		
		return fileCount;
	}

	/**
	 * Stop the execution of this runnable
	 */
	public void stop() {
		logger.info("Stopping peer...");
		
		// shut down peer
		// TODO
	}
}
