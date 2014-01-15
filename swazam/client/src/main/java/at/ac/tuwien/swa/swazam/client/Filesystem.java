package at.ac.tuwien.swa.swazam.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Filesystem {

	public Filesystem() {
			
	}
	
	protected List<File> getMusicFiles(String musicDir) {
		ArrayList<File> files = new ArrayList<File>();
		
		File parent = new File(musicDir);
		
		for (File f : parent.listFiles()) {
			files.add(f);
		}
		
		return files;
	}
}
