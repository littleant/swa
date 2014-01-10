package at.ac.tuwien.swa.swazam.server;

import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;

public class PeerMQTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
			ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.start();
			ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ActiveMQDestination queue = (ActiveMQDestination) session.createQueue(PeerRequest.QUEUE_NAME);
			System.out.println("Create Producer");
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			System.out.println("Create Message");
			ObjectMessage message = session.createObjectMessage(new String("heyho"));
			System.out.println("Set JMS Type");
			//message.setJMSType(String.class.toString());
			System.out.println("Send Message");
			producer.send(message);
			System.out.println("Done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}