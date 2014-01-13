package at.ac.tuwien.swa.swazam.network;

import at.ac.tuwien.swa.swazam.server.PeerRequest;

public interface NetworkManager {

	public abstract void sendRequest(PeerRequest request);

	public abstract void disconnect();

}
