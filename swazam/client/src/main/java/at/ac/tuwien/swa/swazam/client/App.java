package at.ac.tuwien.swa.swazam.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import at.ac.tuwien.swa.swazam.server.ClientRequest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        if(System.getSecurityManager() == null)
        	System.setSecurityManager(new SecurityManager());
        
        try {
			Registry registry = LocateRegistry.getRegistry(ClientRequest.REGISTRY_PORT);
			ClientRequest clientRequest = (ClientRequest) registry.lookup(ClientRequest.REGISTRY_NAME);
			String result = clientRequest.doSomething("whazzzup");
			System.out.println("Result: " + result);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
