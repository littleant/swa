package at.ac.tuwien.swa.swazam.peer;

import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

import at.ac.tuwien.swa.swazam.server.PeerMessage;

public class PeerMQTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Active MQ Initialization - once per peer
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
			ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.start();
			ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ActiveMQDestination queue = (ActiveMQDestination) session.createQueue(PeerMessage.QUEUE_NAME);
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			// Message to server when song was found
			ObjectMessage message = session.createObjectMessage(new PeerMessage("Peer1", "New York, New York", "Frank Sinatra"));
			message.setStringProperty(PeerMessage.REQUEST_IDENTIFIER_NAME, "Request1"); //TODO use given request id from server
			producer.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
