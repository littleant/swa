package at.ac.tuwien.swa.swazam.network.p2p;

import java.util.List;
import java.util.concurrent.ExecutorService;

import at.ac.tuwien.swa.swazam.network.ExecutorFactory;
import at.ac.tuwien.swa.swazam.network.NetworkManager;

public class P2PManager implements NetworkManager, PeerListener {

	private ExecutorService executor;
	private PeerRouter router;
	private JoinListener joinListener;

	public P2PManager(int port) {
		executor = ExecutorFactory.getExectutor();
		router = new PeerRouter();
		joinListener = new JoinListener(this, port);
		executor.execute(joinListener);
	}

	@Override
	public void register(Peer peer) {
		router.add(peer);
	}
	
	public void sendRequest(String request) {
		List<PeerConnector> connectors = router.getDestinations(request);
		for(PeerConnector c : connectors) {
			c.sendAsync(request);
		}
	}
	
	public void disconnect() {
		joinListener.stop();
		executor.shutdownNow();
	}
}