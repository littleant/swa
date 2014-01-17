package at.ac.tuwien.swa.swazam.server;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;

import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

public class MessagingConnector implements MessageListener {
	
	private ActiveMQSession activeMQSession;
	private ActiveMQDestination activeMQDestination;
	
	private static MessagingConnector instance;
	
	private MessagingConnector() {
		super();
	}
	
	public static MessagingConnector getInstance() {
		if(instance == null)
			instance = new MessagingConnector();
		return instance;
	}

	public void init() {
		BrokerService brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		try {
			brokerService.addConnector(PeerMessage.ACTIVEMQ_CONNECTOR);
			brokerService.start();
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
			ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.start();
			activeMQSession = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			activeMQDestination = (ActiveMQDestination) activeMQSession.createQueue(PeerMessage.QUEUE_NAME);
			System.out.println("Message queue up and running");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public PeerMessage receiveMessage(String messageSelector) throws SongNotFoundException {
		MessageConsumer messageConsumer = null;
		PeerMessage peerMessage = null;
		try {
			messageConsumer = activeMQSession.createConsumer(activeMQDestination, messageSelector);
			ObjectMessage message = (ObjectMessage) messageConsumer.receive(5000);
			if (message == null)
				throw new SongNotFoundException("Song not found - request timed out");
			if(message.getObject() instanceof PeerMessage)
				peerMessage = (PeerMessage) message.getObject();
		} catch (JMSException e) {
			throw new SongNotFoundException("Song not found due to technical issues.");
		}
		return peerMessage;
	}

	public ActiveMQSession getActiveMQSession() {
		return activeMQSession;
	}

	public ActiveMQDestination getActiveMQDestination() {
		return activeMQDestination;
	}

	public void setActiveMQSession(ActiveMQSession activeMQSession) {
		this.activeMQSession = activeMQSession;
	}

	public void setActiveMQDestination(ActiveMQDestination activeMQDestination) {
		this.activeMQDestination = activeMQDestination;
	}
	
}
