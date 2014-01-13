package at.ac.tuwien.swa.swazam.peer;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.swa.swazam.server.PeerMessage;

public class MessagingConnector {
	final Logger logger = LoggerFactory.getLogger(MessagingConnector.class);
	private ActiveMQSession session;
	private MessageProducer producer;

	public MessagingConnector() {
		try {
			init();
		} catch (JMSException e) {
			logger.error(e.getMessage());
		}
	}

	private void init() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				ActiveMQConnection.DEFAULT_BROKER_URL);
		ActiveMQConnection connection = (ActiveMQConnection) connectionFactory
				.createConnection();
		connection.start();
		session = (ActiveMQSession) connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		ActiveMQDestination queue = (ActiveMQDestination) session
				.createQueue(PeerMessage.QUEUE_NAME);
		producer = session.createProducer(queue);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}

	public void sendMessage(String correlationId, PeerMessage peerMessage) {
		logger.info("Sending message", peerMessage);
		ObjectMessage message;
		try {
			message = session.createObjectMessage(new PeerMessage(peerMessage
					.getUserId(), peerMessage.getTitle(), peerMessage
					.getArtist()));
			message.setStringProperty(PeerMessage.REQUEST_IDENTIFIER_NAME,
					correlationId);
			producer.send(message);
		} catch (JMSException e) {
			logger.error(e.getMessage());
		}

	}
}
