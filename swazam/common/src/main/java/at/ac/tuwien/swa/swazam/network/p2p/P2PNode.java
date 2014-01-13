package at.ac.tuwien.swa.swazam.network.p2p;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import at.ac.tuwien.swa.swazam.network.ExecutorFactory;
import at.ac.tuwien.swa.swazam.network.NetworkNode;
import at.ac.tuwien.swa.swazam.network.RequestHandler;
import at.ac.tuwien.swa.swazam.server.PeerRequest;

public class P2PNode implements PeerListener, NetworkNode, RequestHandler {

	private ManagerConnector manager;
	private ExecutorService executor;
	private PeerRouter router;
	private JoinListener joinListener;
	private List<RequestHandler> handlers;

	public P2PNode(String host, int port) {
		executor = ExecutorFactory.getExectutor();
		router = new PeerRouter();

		joinListener = new JoinListener(this);
		executor.execute(joinListener);

		manager = new ManagerConnector(host, port, this);
		executor.execute(manager);
		handlers = new ArrayList<>();
	}

	@Override
	public void handle(PeerRequest message) {
		for (RequestHandler handler : handlers) {
			handler.handle(message);
		}
		sendRequest(message);
	}

	public void reconnect(InetSocketAddress addr) {
		System.out.println("reconnecting: " + addr.toString());
		manager = new ManagerConnector(addr.getHostName(), addr.getPort(), this);
		executor.execute(manager);
	}

	public void sendRequest(PeerRequest request) {
		List<PeerMessenger> connectors = router.getDestinations(request);
		for (PeerMessenger c : connectors) {
			c.sendAsync(request);
		}
	}

	@Override
	public void disconnect() {
		joinListener.stop();
		executor.shutdownNow();
	}

	@Override
	public void register(PeerConnector peer) {
		router.add(peer);
	}

	public int getListenerPort() {
		return joinListener.getPortInUse();
	}

	@Override
	public void registerRequestHandler(RequestHandler handler) {
		if (handler == null)
			return;

		handlers.add(handler);
	}
}
