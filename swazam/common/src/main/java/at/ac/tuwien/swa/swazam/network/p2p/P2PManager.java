package at.ac.tuwien.swa.swazam.network.p2p;

import java.util.List;
import java.util.concurrent.ExecutorService;

import at.ac.tuwien.swa.swazam.network.ExecutorFactory;
import at.ac.tuwien.swa.swazam.network.NetworkManager;
import at.ac.tuwien.swa.swazam.server.PeerRequest;

public class P2PManager implements NetworkManager, PeerListener {

	public static int DEFAULT_PORT = 8888;
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
	public void register(PeerConnector peer) {
		router.add(peer);
	}
	
	@Override
	public void sendRequest(PeerRequest request) {
		List<PeerMessenger> connectors = router.getDestinations(request);
		for(PeerMessenger c : connectors) {
			c.sendAsync(request);
		}
	}
	
	@Override
	public void disconnect() {
		joinListener.stop();
		executor.shutdownNow();
	}
}