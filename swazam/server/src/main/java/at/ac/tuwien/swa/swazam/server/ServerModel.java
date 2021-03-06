package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerModel {
	
	private MessagingConnector messagingConnector;

	public ServerModel() {
		super();
		messagingConnector = MessagingConnector.getInstance();
	}
	
	public void init() {
		messagingConnector.init();
		initClientConnection();
	}
	
	private void initClientConnection() {
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		
		try {
			ClientRequest clientRequest = new ClientRequestImpl(messagingConnector.getActiveMQSession(), messagingConnector.getActiveMQDestination());
			ClientRequest clientStub = (ClientRequest) UnicastRemoteObject.exportObject(clientRequest, ClientRequest.REGISTRY_PORT);
			Registry registry = LocateRegistry.createRegistry(ClientRequest.REGISTRY_PORT);
			registry.rebind(ClientRequest.REGISTRY_NAME, clientStub);
			System.out.println("Registry " + ClientRequest.REGISTRY_NAME + " bound");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
