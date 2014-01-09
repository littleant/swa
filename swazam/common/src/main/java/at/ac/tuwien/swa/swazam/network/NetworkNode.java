package at.ac.tuwien.swa.swazam.network;

public interface NetworkNode {

	void disconnect();
	void registerRequestHandler(RequestHandler handler);
}
