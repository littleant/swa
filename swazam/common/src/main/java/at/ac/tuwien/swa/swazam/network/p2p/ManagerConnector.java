package at.ac.tuwien.swa.swazam.network.p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Connector for Peers to connect to the NetworkManager
 * 
 * @author x.zhang
 * 
 */
class ManagerConnector implements Runnable {

	private String host;
	private int port;
	private P2PNode node;

	public ManagerConnector(String host, int port, P2PNode node) {
		this.host = host;
		this.port = port;
		this.node = node;
	}

	@Override
	public void run() {
		try (Socket socket = new Socket(host, port);
				ObjectOutputStream output = new ObjectOutputStream(
						socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(
						socket.getInputStream())) {

			// send service port of this peer to the manager
			output.writeInt(node.getListenerPort());
			output.flush();

			Object obj = null;
			while ((obj = input.readObject()) != null) {
				if (obj instanceof String) {
					node.handle((String) obj);
				} else if (obj instanceof InetSocketAddress) {
					System.out.println("reconnect");
					node.reconnect((InetSocketAddress) obj);
					return;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
