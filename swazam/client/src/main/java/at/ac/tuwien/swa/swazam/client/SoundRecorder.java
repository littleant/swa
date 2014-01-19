package at.ac.tuwien.swa.swazam.client;

import java.io.File;
import java.util.List;

import at.ac.tuwien.swa.swazam.fingerprintgenerator.Filesystem;

public class SoundRecorder 
{
	
	Filesystem filesys;
	
	public SoundRecorder()
	{
		filesys = new Filesystem();
	}
	
	public List<File> getRecords()
	{
		return filesys.listMusicFiles("../music/");
	}
	
	
}

