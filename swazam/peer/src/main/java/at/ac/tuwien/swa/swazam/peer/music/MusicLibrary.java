package at.ac.tuwien.swa.swazam.peer.music;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.swazam.peer.database.DatabaseConnector;

/**
 * Music file and fingerprint interface
 * 
 * @author Andreas
 */
public class MusicLibrary {
	static final Logger logger = LoggerFactory.getLogger(MusicLibrary.class);
	private DatabaseConnector databaseConnector;

	public MusicLibrary() {
		databaseConnector = new DatabaseConnector();
	}

	/**
	 * Retrieve all fingerprints from the database
	 * 
	 * @return Map of fingerprints and their names
	 */
	public Map<Fingerprint, String> getFingerprints() {
		Map<Fingerprint, String> fingerprints = databaseConnector
				.getFingerprints();
		return fingerprints;
	}
	
	public String identify(Fingerprint print) {
		return databaseConnector.identifyFingerprint(print);
	}

	/**
	 * Fingerprint the music on the disk
	 * 
	 * @return Return the number of fingerprints generated
	 */
	public int fingerprintDisk() {
		logger.info("Getting files and directories to fingerprint...");
		Filesystem filesystem = new Filesystem();
		File[] files = filesystem.listMusicFiles();
		logger.info("{} location(s) found.", files.length);

		logger.info("Fingerprinting files...");
		Map<String, Fingerprint> fingerprints = this.fingerprintFiles(files);
		logger.info("{} fingerprints generated.", fingerprints.size());

		logger.info("Saving fingerprints in database...");
		databaseConnector.saveFingerprints(fingerprints);
		logger.info("Fingerprints saved.");

		return fingerprints.size();
	}

	/**
	 * Fingerprint all files in the music-directory
	 * 
	 * @return Fingerprints of the files
	 */
	protected Map<String, Fingerprint> fingerprintFiles(File[] files) {
		FingerprintGenerator fingerprintGenerator = new FingerprintGenerator();
		Map<String, Fingerprint> fingerprints = fingerprintGenerator
				.fingerprintFiles(files);

		return fingerprints;
	}
}
