package at.ac.tuwien.peer.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;

public class DatabaseTest {

	@Test
	public void testSaveFingerprintsAndIdentifyFingerprint() {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("../music/01 - Anitek - Tab _ Anitek - Opaque.mp3"));
			Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
			Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
			fingerprints.put("test name 1", fingerprint);
			
			Database database = new Database();
			database.saveFingerprints(fingerprints);
			
			assertEquals("test name 1", database.identifyFingerprint(fingerprint));
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testSaveFingerprintsAndIdentifyFingerprintTwice() {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("../music/01 - Anitek - Tab _ Anitek - Opaque.mp3"));
			Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
			Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
			fingerprints.put("test name 1", fingerprint);
			
			Database database = new Database();
			database.saveFingerprints(fingerprints);
			database.saveFingerprints(fingerprints);
			
			assertEquals("test name 1", database.identifyFingerprint(fingerprint));
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
	}
	

	
	@Test
	public void testSaveFingerprintsAndIdentifyFingerprintTwoDifferentOnes() {
		try {
			String [] files = {"../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", "../music/02 - Anitek - Tab _ Anitek - Physical Graffiti.mp3"};
			for (String file : files) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(file));
				Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
				Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
				fingerprints.put(file, fingerprint);
				
				Database database = new Database();
				database.saveFingerprints(fingerprints);
				
				assertEquals(file, database.identifyFingerprint(fingerprint));
			}
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
	}
}
