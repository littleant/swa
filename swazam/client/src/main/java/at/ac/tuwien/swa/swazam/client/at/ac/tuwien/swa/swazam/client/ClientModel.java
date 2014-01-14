package at.ac.tuwien.swa.swazam.client;


import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import at.ac.tuwien.swa.swazam.server.ClientRequest;
import at.ac.tuwien.swa.swazam.server.ClientRequestParam;
import at.ac.tuwien.swa.swazam.server.ClientRequestResult;
import at.ac.tuwien.swa.swazam.server.exception.NoCoinsException;
import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;


public class ClientModel 
{
	private SoundRecorder soundrec;
	private ClientViewModel clientview;
	
	private ClientRequest clientRequest; 
	
	public ClientModel()
	{
		soundrec = new SoundRecorder();	
		clientview = new ClientViewModel(this);
		
		
	}
	
	public void startClient()
	{
		clientview.showView(soundrec.getRecords());
	}
	
	public void sendRequest(File music)
	{
		FingerprintGenerator fingerprint = new FingerprintGenerator();
		
		
        if(System.getSecurityManager() == null)
         	System.setSecurityManager(new SecurityManager());
         
         try {
        	 
        			// RMI initialization - once per client
        		Registry registry = LocateRegistry.getRegistry(ClientRequest.REGISTRY_PORT);
        		clientRequest = (ClientRequest) registry.lookup(ClientRequest.REGISTRY_NAME);
        			
    			// search request for server
    			ClientRequestResult result = clientRequest.submitRequest(new ClientRequestParam(clientview.view.getId(), fingerprint.fingerprintFile(music)));
    			System.out.println(result);
    			clientview.printResolvedSong(result.getTitle());
    		} catch (RemoteException | NotBoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SongNotFoundException e) {
    			System.out.println(e.getMessage());
    		} catch (NoCoinsException e) {
    			System.out.println(e.getMessage());
    		}
	}
	
	 
	
	
}