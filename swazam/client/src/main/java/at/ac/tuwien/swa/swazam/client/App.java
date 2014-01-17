package at.ac.tuwien.swa.swazam.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.swazam.server.ClientRequest;
import at.ac.tuwien.swa.swazam.server.ClientRequestParam;
import at.ac.tuwien.swa.swazam.server.ClientRequestResult;
import at.ac.tuwien.swa.swazam.server.exception.NoCoinsException;
import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

//import at.ac.tuwien.swa.swazam.server.ClientRequest;
//import at.ac.tuwien.swa.swazam.server.ClientRequestParam;
//import at.ac.tuwien.swa.swazam.server.ClientRequestResult;

/**
 * Hello world!
 *
 */
public class App 
{

	/*
	 * start with these command line options:
	 * -Djava.security.policy=${project_loc:client}/src/main/java/at/ac/tuwien/swa/swazam/client/client.policy
	 */
    public static void main( String[] args )
    {
    	if (args.length != 1)
    	{
    		System.out.println("Usage: client <id : long>");
    	}
    	else
    	{
    		System.out.println( "Started App" );
    		try{
    		ClientModel client = new ClientModel(Long.parseLong(args[0]));
    		}catch(Exception e)
    		{
    			System.out.println("Usage: client <id : long>");
    			System.err.println(e);
    		}
    	}
    	//  	client.startClient();
     //   client.setVisible(true);
        
//        if(System.getSecurityManager() == null)
//        	System.setSecurityManager(new SecurityManager());
//        
//        try {
//			Registry registry = LocateRegistry.getRegistry(ClientRequest.REGISTRY_PORT);
//			ClientRequest clientRequest = (ClientRequest) registry.lookup(ClientRequest.REGISTRY_NAME);
//		//	String answer = clientRequest.doSomething("whazzzup");
//		//	System.out.println("Result: " + answer);
//			ClientRequestResult result;
//			try {
//				result = clientRequest.submitRequest(new ClientRequestParam(1,new Fingerprint()));
//				System.out.println(result);
//			} catch (SongNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NoCoinsException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (RemoteException | NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
