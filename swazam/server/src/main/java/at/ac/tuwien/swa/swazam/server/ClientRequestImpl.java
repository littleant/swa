package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

public class ClientRequestImpl implements ClientRequest {
	
	private MessageConsumer messageConsumer;
	
	public ClientRequestImpl() {
		super();
	}
	
	public ClientRequestImpl(MessageConsumer messageConsumer) {
		super();
		this.messageConsumer = messageConsumer;
	}

	@Override
	public String doSomething(String someParameter) throws RemoteException {
		// TODO remove test method
		return "Hello " + someParameter;
	}

	@Override
	public ClientRequestResult submitRequest(ClientRequestParam param) throws SongNotFoundException {

		//TODO fancy peer network call + wait for result
		//implement kind of timeout? max. 5 seconds or so???
		PeerMessage peerMessage = null;
		
		try {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(peerMessage != null)
			return new ClientRequestResult(peerMessage.getTitle(), peerMessage.getArtist());
		else
			throw new SongNotFoundException("Song not found due to technical issues.");
	}

}
