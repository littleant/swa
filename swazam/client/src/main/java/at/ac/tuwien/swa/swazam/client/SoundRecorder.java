package at.ac.tuwien.swa.swazam.client;

import java.io.File;
import java.util.List;

public class SoundRecorder 
{
	
	Filesystem filesys;
	
	public SoundRecorder()
	{
		filesys = new Filesystem();
	}
	
	public List<File> getRecords()
	{
		return filesys.getMusicFiles("../music/");
	}
	
	
}

