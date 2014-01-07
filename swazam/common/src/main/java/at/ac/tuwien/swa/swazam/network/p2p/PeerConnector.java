package at.ac.tuwien.swa.swazam.network.p2p;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import at.ac.tuwien.swa.swazam.network.ExecutorFactory;
import at.ac.tuwien.swa.swazam.network.NetworkConnector;

public class PeerConnector implements NetworkConnector {

	private PeerRouter router;
	private Peer peer;
	private ExecutorService executor;
	
	public PeerConnector(PeerRouter peerRouter, Peer peer) {
		this.router = peerRouter;
		this.peer = peer;
		this.executor = ExecutorFactory.getExectutor();
	}

	public void sendAsync(final String request) {
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
