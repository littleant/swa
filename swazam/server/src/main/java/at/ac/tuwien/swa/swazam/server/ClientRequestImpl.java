package at.ac.tuwien.swa.swazam.server;

import java.rmi.RemoteException;

public class ClientRequestImpl implements ClientRequest {

	@Override
	public String doSomething(String someParameter) throws RemoteException {
		// TODO remove test method
		return "Hello " + someParameter;
	}

	@Override
	public ClientRequestResult submitRequest(ClientRequestParam param) {

		//TODO fancy peer network call + wait for result
		//implement kind of timeout? max. 5 seconds or so???
		
		return new ClientRequestResult("Artist for fingerprint " + param.getFingerprint(), "Title for fingerprint " + param.getFingerprint());
	}

}
