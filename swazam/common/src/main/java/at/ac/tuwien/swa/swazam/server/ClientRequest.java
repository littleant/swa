package at.ac.tuwien.swa.swazam.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRequest extends Remote {
	
	public static final String REGISTRY_NAME = "ClientRequest";
	public static final int REGISTRY_PORT = 10000;
	
	public String doSomething(String someParameter) throws RemoteException;
	public ClientRequestResult submitRequest(ClientRequestParam param) throws RemoteException;
}
