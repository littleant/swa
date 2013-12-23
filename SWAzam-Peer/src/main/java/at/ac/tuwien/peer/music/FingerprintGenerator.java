package at.ac.tuwien.peer.music;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;

/**
 * Generator to generate fingerprints of music files
 * 
 * @author Andreas
 */
public class FingerprintGenerator {
	final Logger logger = LoggerFactory.getLogger(FingerprintGenerator.class);
	
	/**
	 * Fingerprint all audio files of the directories
	 * 
	 * @param files Files or directories to fingerprint
	 * @return Fingerprints of the files
	 */
	public Map<String, Fingerprint> fingerprintFiles(File[] files) {
		Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
		
		logger.debug("Got {} file(s)!", files.length);
		
		for (File file : files) {
			if (file == null) {
				// skip file
				continue;
			}
			
			if (file.isDirectory()) {
	            logger.debug("Directory: {}", file.getName());
	            fingerprints.putAll(this.fingerprintFiles(file.listFiles()));
	        } else {
	            logger.debug("File: {}", file.getName());
	            
	            // fingerprint file
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(file);
		            Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
		            
		            fingerprints.put(file.getName(), fingerprint);
				} catch (UnsupportedAudioFileException e) {
					logger.debug(e.getLocalizedMessage(), e);
				} catch (IOException e) {
					logger.debug(e.getLocalizedMessage(), e);
				}
	        }
		}
		
		return fingerprints;
	}
}
