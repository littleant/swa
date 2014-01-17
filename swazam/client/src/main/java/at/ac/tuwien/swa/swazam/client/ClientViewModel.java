package at.ac.tuwien.swa.swazam.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class ClientViewModel 
{
	ClientView view;
	ClientModel model;
	List<File> musicFiles;
	
	public ClientViewModel(ClientModel model)
	{
		this.model = model;
		musicFiles = new ArrayList<File>();
		view = new ClientView(this);
	}
	
	public void showView(List<File> files)
	{
		view.getCmodel().removeAllElements();
		for (File f : files)
		{
			view.getCmodel().addElement(f.getName());
		}
		musicFiles = files;
		view.setVisible(true);
	}
	
	public void closeView()
	{
		view.setVisible(false);
	}
	
	public void startMusicRecognition(String name)
	{
		File fileBuffer = new File("wrong");
		for (File f : musicFiles)
		{
			if (f.getName().equals(name))
			{
				fileBuffer = f;
				break;
			}
			
		}
		System.out.println("recognition for File: "+fileBuffer.getAbsolutePath());
		model.sendRequest(fileBuffer);
		
	}
	
	public void printResolvedSong(String name)
	{
		view.getResultLabel().setText(name);
	}
	
}