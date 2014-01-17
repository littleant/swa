package at.ac.tuwien.swa.swazam.server;

import java.util.UUID;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

import at.ac.tuwien.swa.swazam.network.p2p.P2PManager;
import at.ac.tuwien.swa.swazam.server.exception.NoCoinsException;
import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

public class ClientRequestImpl implements ClientRequest {
	
	private MessageListener messageListener;
	
	public ClientRequestImpl(ActiveMQSession session, ActiveMQDestination queue) {
		super();
		messageListener = MessagingConnector.getInstance();
	}

	@Override
	public ClientRequestResult submitRequest(ClientRequestParam param) throws SongNotFoundException, NoCoinsException {
		String requestIdentifier = UUID.randomUUID().toString();
		String messageSelector = PeerMessage.REQUEST_IDENTIFIER_NAME + "='" + requestIdentifier + "'";
		
		//TODO check if user has coins left to search for songs
		if (param.getUserId() == 12345l) {
			throw new NoCoinsException("No coins left for user - search request not permitted");
		} else {
			//TODO store search request in database, decrement coins for user
		}
		
		P2PManager manager = new P2PManager(P2PManager.DEFAULT_PORT);
		manager.sendRequest(new PeerRequest(requestIdentifier, param.getFingerprint()));
		
		PeerMessage peerMessage = messageListener.receiveMessage(messageSelector);

		if(peerMessage != null) {
			//TODO add coin for peer user and store answer in database
			return new ClientRequestResult(peerMessage.getTitle(), peerMessage.getArtist());
		}
		else
			throw new SongNotFoundException("Song not found due to technical issues.");
	}

}
