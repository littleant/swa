package at.ac.tuwien.swa.swazam.network;

import at.ac.tuwien.swa.swazam.server.PeerRequest;

public interface RequestHandler {
	boolean handle(PeerRequest request);
}
