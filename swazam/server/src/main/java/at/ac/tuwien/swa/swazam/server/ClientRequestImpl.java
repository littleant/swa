package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

import at.ac.tuwien.swa.swazam.server.exception.NoCoinsException;
import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

public class ClientRequestImpl implements ClientRequest {
	
	private ActiveMQSession session;
	private ActiveMQDestination queue;
	
	public ClientRequestImpl() {
		super();
	}
	
	public ClientRequestImpl(ActiveMQSession session, ActiveMQDestination queue) {
		super();
		this.session = session;
		this.queue = queue;
	}

	@Override
	public String doSomething(String someParameter) throws RemoteException {
		// TODO remove test method
		return "Hello " + someParameter;
	}

	@Override
	public ClientRequestResult submitRequest(ClientRequestParam param) throws SongNotFoundException, NoCoinsException {
		
		MessageConsumer messageConsumer = null;
		String messageSelector = PeerMessage.REQUEST_IDENTIFIER_NAME + "='Request1'";
		//TODO generate unique request identifier
		//UUID.randomUUID().toString()
		
		//TODO peer network call
		//parameters for call:
		// - fingerprint
		// - unique request identifier
		
		//timeout? max. 5 seconds or so???
		//what to do after receipt of valid peer message?
		//inform other peers about success or not?
		
		//TODO check if user has coins left to search for songs
		if (param.getUserId() == 12345l) {
			throw new NoCoinsException("No coins left for user - search request not permitted");
		} else {
			//TODO decrement coin number for user
		}
		
		PeerMessage peerMessage = null;
		
		try {
			messageConsumer = session.createConsumer(queue, messageSelector);
			ObjectMessage message = (ObjectMessage) messageConsumer.receive(5000);
			if (message == null)
				throw new SongNotFoundException("Song not found - request timed out");
			if(message.getObject() instanceof PeerMessage)
				peerMessage = (PeerMessage) message.getObject();
		} catch (JMSException e) {
			throw new SongNotFoundException("Song not found due to technical issues.");
		}

		if(peerMessage != null) {
			//TODO add coin for peerMessage.getPeerIdentifier()
			return new ClientRequestResult(peerMessage.getTitle(), peerMessage.getArtist());
		}
		else
			throw new SongNotFoundException("Song not found due to technical issues.");
	}

}
