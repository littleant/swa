package at.ac.tuwien.peer.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Access to music files and directories
 * 
 * @author Andreas
 */
public class Filesystem {
	final Logger logger = LoggerFactory.getLogger(Filesystem.class);

	private String[] musicDirectories = {"../music/"};

	public Filesystem() {
		// read peer.properties file to configure music directories
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("peer.properties"));
		} catch (FileNotFoundException e) {
			logger.error(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		String key = "music-directories";
		if (properties.containsKey(key)) {
			this.musicDirectories = properties.getProperty(key).split(File.pathSeparator);
		} else {
			logger.info("Falling back to default music-directory");
		}
	}
	
	/**
	 * Retrieve a list of music files and directories from the file system
	 * 
	 * @return List of music files and directories to scan
	 */
	protected File[] listMusicFiles() {
		File[] files = new File[this.musicDirectories.length];
		
		for (int i = 0; i < this.musicDirectories.length; i++) {
			File file = new File(this.musicDirectories[i]);
			files[i] = file;
		}
		
		return files;
	}
}
