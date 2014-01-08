package at.ac.tuwien.swa.swazam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;
import ac.at.tuwien.infosys.swa.audio.SubFingerprint;
import at.ac.tuwien.swa.swazam.peer.database.Database;

public class DatabaseTest {

	@Test
	public void testSaveFingerprintsAndIdentifyFingerprint() {
		Database database = new Database();
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3"));
			Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
			Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
			fingerprints.put("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", fingerprint);
			database.saveFingerprints(fingerprints);
			
			assertEquals("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", database.identifyFingerprint(fingerprint));
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		
		database.close();
	}
	
	@Test
	public void testSaveFingerprintsAndIdentifyFingerprintTwice() {
		Database database = new Database();
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3"));
			Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
			Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
			fingerprints.put("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", fingerprint);
			
			database.saveFingerprints(fingerprints);
			database.saveFingerprints(fingerprints);
			
			assertEquals("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", database.identifyFingerprint(fingerprint));
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		
		database.close();
	}
	
	@Test
	public void testSaveFingerprintsAndIdentifyFingerprintTwoDifferentOnes() {
		Database database = new Database();
		try {
			String [] files = {"../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", "../../music/02 - Anitek - Tab _ Anitek - Physical Graffiti.mp3"};
			for (String file : files) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(file));
				Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
				Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
				fingerprints.put(file, fingerprint);
				
				database.saveFingerprints(fingerprints);
				
				assertEquals(file, database.identifyFingerprint(fingerprint));
			}
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		
		database.close();
	}
	
	@Test
	public void testGetFingerprints() {
		Database database = new Database();
		try {
			String [] files = {"../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3", "../../music/02 - Anitek - Tab _ Anitek - Physical Graffiti.mp3"};
			Map<String, Fingerprint> fingerprints = new HashMap<String, Fingerprint>();
			for (String file : files) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File(file));
				Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
				fingerprints.put(file, fingerprint);
			}
			
			database.saveFingerprints(fingerprints);
			
			Map<Fingerprint, String> dbFingerprints = database.getFingerprints();
			//assertEquals(files.length, dbFingerprints.size());
			
			for (Fingerprint dbFingerprint : dbFingerprints.keySet()) {
				boolean found = false;
				for (Fingerprint fingerprint : fingerprints.values()) {
					if (dbFingerprint.match(fingerprint) == 0.0) {
						// compare name
						Iterator<String> names = fingerprints.keySet().iterator();
						while (names.hasNext()) {
							String name = names.next();

							if (fingerprints.get(name).match(dbFingerprint) == 0.0) {
								assertEquals(dbFingerprints.get(dbFingerprint), name);
								
								found = true;
							}
						}
					}
				}
				
				if (!found) {
					fail("Fingerprint didn't match a fingerprint in the database.");
				}
			}
		} catch (UnsupportedAudioFileException e) {
			fail(e.getLocalizedMessage());
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		
		database.close();
	}
	
	@Test
	public void testDeserializeSubFingerprint() {
		// FIXME: Integer.MIN_VALUE doesn't work. Why? Doesn't matter with SubFingerprint.match(), but with SubFingerprint.equals()
		SubFingerprint subFingerprint = new SubFingerprint(Integer.MAX_VALUE);
		String serialized = subFingerprint.toString();
		
		Database database = new Database();
		SubFingerprint deserialized = database.deserializeSubFingerprint(serialized);
		
		assertEquals(serialized, deserialized.toString());
		
		database.close();
	}
	
	@Test
	public void testDeserializeFingerprint() {
		Database database = new Database();
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File("../../music/01 - Anitek - Tab _ Anitek - Opaque.mp3"));
			Fingerprint fingerprint = FingerprintSystem.fingerprint(audio);
			
			String serialized = fingerprint.toString();
			Fingerprint deserialized = database.deserializeFingerprint(fingerprint.getStartTime(), fingerprint.getShiftDuration(), serialized);
			
			assert(deserialized.equals(fingerprint));
		} catch (UnsupportedAudioFileException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		database.close();
	}
}
