package at.ac.tuwien.swa.swazam.peer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.swa.swazam.network.NetworkNode;
import at.ac.tuwien.swa.swazam.network.RequestHandler;
import at.ac.tuwien.swa.swazam.network.p2p.P2PNode;
import at.ac.tuwien.swa.swazam.peer.music.MusicLibrary;
import at.ac.tuwien.swa.swazam.server.PeerMessage;
import at.ac.tuwien.swa.swazam.server.PeerRequest;

/**
 * P2P peer that fingerprints music files and connects to other peers to receive
 * music-identification-requests
 * 
 * @author Andreas
 */
public class Peer implements Runnable, RequestHandler {
	final Logger logger = LoggerFactory.getLogger(Peer.class);
	private String host;
	private int port;
	private NetworkNode node;
	private MusicLibrary musicLibrary;
	private MessagingConnector msgConnector;
	private long userId;

	public Peer(String host, int port) {
		this.host = host;
		this.port = port;
		this.userId = -1;
	}
	
	public void login(String username) {
		
	}

	@Override
	public void run() {
		logger.info("Starting peer...");

		musicLibrary = new MusicLibrary();
		musicLibrary.fingerprintDisk();
		node = new P2PNode(host, port);
		node.registerRequestHandler(this);
		msgConnector = new MessagingConnector();
		logger.info("Peer stopped.");
	}

	/**
	 * Stop the execution of this runnable
	 */
	public void stop() {
		logger.info("Stopping peer...");
		if (node != null) {
			node.disconnect();
		}
	}

	@Override
	public boolean handle(PeerRequest request) {
		logger.info("Handling request: ", request);
		if (musicLibrary == null) {
			return false;
		}

		logger.info("Identifying fingerprint");
		String name = musicLibrary.identify(request.getFingerprint());

		if (name == null) {
			logger.info("No match found for fingerprint");
			return false;
		}
		logger.info("Match found for fingerprint");
		PeerMessage msg = new PeerMessage(this.userId, name, "");
		msgConnector.sendMessage(request.getRequestIdentifier(), msg);
		return true;
	}
}
