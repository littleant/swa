package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;

public class ClientRequestImpl implements ClientRequest {

	@Override
	public String doSomething(String someParameter) throws RemoteException {
		// TODO Auto-generated method stub
		return "Hello " + someParameter;
	}

}
