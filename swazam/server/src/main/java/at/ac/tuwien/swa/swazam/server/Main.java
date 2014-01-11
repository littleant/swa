package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;

public class Main {

	private static MessageConsumer messageConsumer;
	
	public static void main(String[] args) {
		initPeerConnection();
		initClientConnection();
	}
	
	private static void initClientConnection() {
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		try {
			ClientRequest clientRequest = new ClientRequestImpl(messageConsumer);
			ClientRequest clientStub = (ClientRequest) UnicastRemoteObject.exportObject(clientRequest, ClientRequest.REGISTRY_PORT);
			Registry registry = LocateRegistry.createRegistry(ClientRequest.REGISTRY_PORT);
			registry.rebind(ClientRequest.REGISTRY_NAME, clientStub);
			System.out.println("Registry " + ClientRequest.REGISTRY_NAME + " bound");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initPeerConnection() {
		BrokerService brokerService = new BrokerService();
		brokerService.setUseJmx(true);
		try {
			brokerService.addConnector(PeerMessage.ACTIVEMQ_CONNECTOR);
			brokerService.start();
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
			ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.start();
			ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ActiveMQDestination queue = (ActiveMQDestination) session.createQueue(PeerMessage.QUEUE_NAME);
			messageConsumer = session.createConsumer(queue);
			System.out.println("Message queue up and running");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
