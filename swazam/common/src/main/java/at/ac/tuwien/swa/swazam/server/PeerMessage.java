package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

public class PeerMessage implements Serializable {

	private static final long serialVersionUID = 8043946694342727187L;
	
	public static final String ACTIVEMQ_CONNECTOR = "tcp://localhost:61616";
	public static final String QUEUE_NAME = "MYQUEUE";
	public static final String REQUEST_IDENTIFIER_NAME = "RequestIdentifier";
	
	//TODO specify concrete data types for interface
	private long userId; // which peer user sent the message
	private String title;
	private String artist;
	
	public PeerMessage() {
		super();
	}
	
	public PeerMessage(long userId, String title, String artist) {
		super();
		this.userId = userId;
		this.title = title;
		this.artist = artist;
	}
	
	public long getUserId() {
		return userId;
	}
	public String getTitle() {
		return title;
	}
	public String getArtist() {
		return artist;
	}
	public void setPeerIdentifier(long userId) {
		this.userId = userId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@Override
	public String toString() {
		final String CRLF = System.getProperty("line.separator");
		return "{PeerMessage" + CRLF +
				"   userId=" + this.userId + CRLF +
				"   title=" + this.title + CRLF +
				"   artist=" + this.artist + CRLF +
				"}";
	}
}
