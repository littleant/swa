package at.ac.tuwien.swa.swazam.server;


public class Main {

	/*
	 * start with these command line options:
	 * -Djava.rmi.server.codebase=${project_loc:server}/target/classes
	 * -Djava.security.policy=${project_loc:server}/src/main/java/at/ac/tuwien/swa/swazam/server/server.policy
	 */
	public static void main(String[] args) {
		ServerModel serverModel = new ServerModel();
		serverModel.initPeerConnection();
		serverModel.initClientConnection();
	}
	
	
}
