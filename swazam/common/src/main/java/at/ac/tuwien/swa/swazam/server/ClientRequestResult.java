package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

public class ClientRequestResult implements Serializable {

	private static final long serialVersionUID = 618271627448998265L;

	//TODO change to business object or leave as simple DTO?
	private String title;
	private String artist;
	
	public ClientRequestResult(String title, String artist) {
		super();
		this.title = title;
		this.artist = artist;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@Override
	public String toString() {
		return "{ClientRequestResult title = \"" + title + "\" artist = \"" + artist + "\"}";
	}
}
