package at.ac.tuwien.swa.swazam.network.p2p;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import at.ac.tuwien.swa.swazam.server.PeerRequest;

/**
 * Router for Peers with no special routing algorithm
 * @author x.zhang
 *
 */
class PeerRouter {

	public List<PeerConnector> superPeers;

	public PeerRouter() {
		superPeers = Collections.synchronizedList(new ArrayList<PeerConnector>());
	}

	public void add(PeerConnector peer) {
		int n = superPeers.size() > 0 ? superPeers.size() : 1;
		float superPeerProbability = 1.0f / n;
		if (Math.random() < superPeerProbability) {
			superPeers.add(peer);
		} else {
			int index = (int) (Math.random() * superPeers.size());
			PeerConnector p = superPeers.get(index);
			peer.redirect(p);
		}
	}

	public void remove(PeerConnector peer) {
		superPeers.remove(peer);
	}

	/**
	 * Returns an arbitrary set of Peers to send the request to
	 * 
	 * @param request
	 * @return
	 */
	public List<PeerMessenger> getDestinations(PeerRequest request) {
		List<PeerMessenger> connectors = new ArrayList<>();
		if (superPeers.size() == 0) {
			return connectors;
		}

		Random random = new Random(System.currentTimeMillis());
		for (PeerConnector p : superPeers) {
			if (random.nextBoolean()) {
				connectors.add(new PeerMessenger(this, p));
			}
		}

		if (connectors.size() == 0) {
			connectors.add(new PeerMessenger(this, superPeers.get(0)));
		}

		return connectors;
	}
}
