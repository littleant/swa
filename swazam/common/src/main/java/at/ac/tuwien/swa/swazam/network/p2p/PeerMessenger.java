package at.ac.tuwien.swa.swazam.network.p2p;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import at.ac.tuwien.swa.swazam.network.ExecutorFactory;
import at.ac.tuwien.swa.swazam.server.PeerRequest;

/**
 * Class for the NetworkMananger to communicate with peers
 * 
 * @author x.zhang
 * 
 */
class PeerMessenger {

	private PeerRouter router;
	private PeerConnector peer;
	private ExecutorService executor;

	public PeerMessenger(PeerRouter peerRouter, PeerConnector peer) {
		this.router = peerRouter;
		this.peer = peer;
		this.executor = ExecutorFactory.getExectutor();
	}

	/**
	 * Sends the given request in a new spawned thread and returns immediately
	 * 
	 * @param request
	 */
	public void sendAsync(final PeerRequest request) {
		System.out.println("Send: " + request);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					peer.send(request);
				} catch (IOException e) {
					router.remove(peer);
				}
			}
		});
	}
}
