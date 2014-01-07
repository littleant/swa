package at.ac.tuwien.swa.swazam.network.p2p;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;

import at.ac.tuwien.swa.swazam.network.ExecutorFactory;

public class P2PNode implements PeerListener {

	private ManagerConnector manager;
	private ExecutorService executor;
	private PeerRouter router;
	private JoinListener joinListener;

	public P2PNode(String host, int port) {
		executor = ExecutorFactory.getExectutor();
		router = new PeerRouter();

		joinListener = new JoinListener(this);
		executor.execute(joinListener);

		manager = new ManagerConnector(host, port, this);
		executor.execute(manager);
	}

	public void handle(String message) {
		System.out.println(message);
		sendRequest(message);
	}

	public void reconnect(InetSocketAddress addr) {
		System.out.println("reconnecting: " + addr.toString());
		manager = new ManagerConnector(addr.getHostName(), addr.getPort(), this);
		executor.execute(manager);
	}

	public void sendRequest(String request) {
		List<PeerConnector> connectors = router.getDestinations(request);
		for (PeerConnector c : connectors) {
			c.sendAsync(request);
		}
	}

	public void disconnect() {
		joinListener.stop();
		executor.shutdownNow();
	}

	@Override
	public void register(Peer peer) {
		router.add(peer);
	}

	public int getListenerPort() {
		return joinListener.getPortInUse();
	}
}
