package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

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
	public ClientRequestResult submitRequest(ClientRequestParam param) throws SongNotFoundException {
		
		MessageConsumer messageConsumer = null;
		String messageSelector = "RequestIdentifier='Request1'"; //TODO generate unique request identifier
		
		//TODO peer network call
		//parameters for call:
		// - fingerprint
		// - unique request identifier
		
		//timeout? max. 5 seconds or so???
		//what to do after receipt of valid peer message?
		//inform other peers about success or not?
		
		PeerMessage peerMessage = null;
		
		try {
			messageConsumer = session.createConsumer(queue, messageSelector);
			ObjectMessage message = (ObjectMessage) messageConsumer.receive(5000);
			if (message == null)
				throw new SongNotFoundException("Song not found - request timed out");
			if(message.getObject() instanceof PeerMessage) {
				System.out.println("PeerMessage received:");
				System.out.println(message.getObject().toString());
				peerMessage = (PeerMessage) message.getObject();
			} else {
				System.out.println("Other message received: " + message.getObject().toString());
			}
		} catch (JMSException e) {
			throw new SongNotFoundException("Song not found due to technical issues.");
		}

		if(peerMessage != null)
			return new ClientRequestResult(peerMessage.getTitle(), peerMessage.getArtist());
		else
			throw new SongNotFoundException("Song not found due to technical issues.");
	}

}
