package at.ac.tuwien.peer.database;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

/**
 * Connector that synchronously connects to the Fingerprint database
 * 
 * @author Andreas
 */
public class DatabaseConnector {
	final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
	
	private Database database = new Database();
	
	public void saveFingerprints(Map<String, Fingerprint> fingerprints) {
		database.saveFingerprints(fingerprints);
	}
	
	public String identifyFingerprint(Fingerprint fingerprint) {
		return database.identifyFingerprint(fingerprint);
	}
	
	public Map<Fingerprint, String> getFingerprints() {
		return database.getFingerprints();
	}
}
