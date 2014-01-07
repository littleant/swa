package at.ac.tuwien.swa.swazam.network.p2p;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PeerRouter {

	public List<Peer> superPeers;

	public PeerRouter() {
		superPeers = Collections.synchronizedList(new ArrayList<Peer>());
	}

	public void add(Peer peer) {
		int n = superPeers.size() > 0 ? superPeers.size() : 1;
		float superPeerProbability = 1.0f / n;
		if (superPeers.size() < 1) {
			superPeers.add(peer);
		} else {
			int index = (int) (Math.random() * superPeers.size());
			Peer p = superPeers.get(index);
			peer.redirect(p);
		}
	}

	public void remove(Peer peer) {
		superPeers.remove(peer);
	}

	public List<PeerConnector> getDestinations(String request) {
		List<PeerConnector> connectors = new ArrayList<>();
		if (superPeers.size() == 0) {
			return connectors;
		}

		Random random = new Random(System.currentTimeMillis());
		for (Peer p : superPeers) {
			if (random.nextBoolean()) {
				connectors.add(new PeerConnector(this, p));
			}
		}

		if (connectors.size() == 0) {
			connectors.add(new PeerConnector(this, superPeers.get(0)));
		}
		
		return connectors;
	}
}
