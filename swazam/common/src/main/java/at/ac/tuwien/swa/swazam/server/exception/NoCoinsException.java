package at.ac.tuwien.swa.swazam.server.exception;

public class NoCoinsException extends Exception {

	private static final long serialVersionUID = 2458519373417794149L;

	public NoCoinsException(String message) {
		super(message);
	}
}
