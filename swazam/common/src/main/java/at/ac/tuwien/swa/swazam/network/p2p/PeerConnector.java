package at.ac.tuwien.swa.swazam.network.p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Class NetworkManager to connect to Peers
 * 
 * @author x.zhang
 * 
 */
class PeerConnector {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private int listenerPort;

	public PeerConnector(Socket socket) throws IOException {
		this.socket = socket;
		inputStream = new ObjectInputStream(socket.getInputStream());
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		listenerPort = inputStream.readInt();
	}

	public void redirect(PeerConnector peer) {
		SocketAddress socketAddress = new InetSocketAddress(peer.getSocket()
				.getInetAddress(), peer.getPort());
		try {
			System.out.println("redirecting");
			outputStream.writeObject(socketAddress);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String request) throws IOException {
		outputStream.writeObject(request);
		outputStream.flush();
	}

	public Socket getSocket() {
		return socket;
	}

	public int getPort() {
		return listenerPort;
	}

	public void disconnect() {
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
