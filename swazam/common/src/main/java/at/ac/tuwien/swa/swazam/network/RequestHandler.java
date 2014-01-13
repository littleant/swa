package at.ac.tuwien.swa.swazam.network;

import at.ac.tuwien.swa.swazam.server.PeerRequest;

public interface RequestHandler {
	void handle(PeerRequest request);
}
