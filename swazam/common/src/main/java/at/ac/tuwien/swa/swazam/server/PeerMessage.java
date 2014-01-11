package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

public class PeerMessage implements Serializable {

	private static final long serialVersionUID = 8043946694342727187L;
	
	public static final String ACTIVEMQ_CONNECTOR = "tcp://localhost:61616";
	public static final String QUEUE_NAME = "MYQUEUE";
	public static final String REQUEST_IDENTIFIER_NAME = "RequestIdentifier";
	
	//TODO specify concrete data types for interface
	private String peerIdentifier; // which peer sent the message
	//TODO peerIdentifier: user id or peer id with matching to user id in server database?
	private String title;
	private String artist;
	
	public PeerMessage() {
		super();
	}
	
	public PeerMessage(String peerIdentifier, String title, String artist) {
		super();
		this.peerIdentifier = peerIdentifier;
		this.title = title;
		this.artist = artist;
	}
	
	public String getPeerIdentifier() {
		return peerIdentifier;
	}
	public String getTitle() {
		return title;
	}
	public String getArtist() {
		return artist;
	}
	public void setPeerIdentifier(String peerIdentifier) {
		this.peerIdentifier = peerIdentifier;
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
				"   peerIdentifier=" + this.peerIdentifier + CRLF +
				"   title=" + this.title + CRLF +
				"   artist=" + this.artist + CRLF +
				"}";
	}
}
