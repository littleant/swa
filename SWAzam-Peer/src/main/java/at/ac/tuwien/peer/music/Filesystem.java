package at.ac.tuwien.peer.music;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Access to music files and directories
 * 
 * @author Andreas
 */
public class Filesystem {
	final Logger logger = LoggerFactory.getLogger(Filesystem.class);

	private final static String[] musicDirectories = {"../music/"};
	
	/**
	 * Retrieve a list of music files and directories from the file system
	 * 
	 * @return List of music files and directories to scan
	 */
	protected File[] listMusicFiles() {
		File[] files = new File[Filesystem.musicDirectories.length];
		for (String directory : Filesystem.musicDirectories) {
			File file = new File(directory);
			files[0] = file;
		}
		
		return files;
	}
}
