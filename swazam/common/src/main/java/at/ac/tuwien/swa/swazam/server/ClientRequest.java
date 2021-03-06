package at.ac.tuwien.swa.swazam.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.ac.tuwien.swa.swazam.server.exception.NoCoinsException;
import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

public interface ClientRequest extends Remote {
	
	public static final String REGISTRY_NAME = "ClientRequest";
	public static final int REGISTRY_PORT = 10000;
	
	public ClientRequestResult submitRequest(ClientRequestParam param) throws RemoteException, SongNotFoundException, NoCoinsException;
}
