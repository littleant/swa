package at.ac.tuwien.swa.swazam.server.exception;

public class SongNotFoundException extends Exception {

	private static final long serialVersionUID = 3920857584221351321L;
	
	public SongNotFoundException(String message) {
		super(message);
	}

}
