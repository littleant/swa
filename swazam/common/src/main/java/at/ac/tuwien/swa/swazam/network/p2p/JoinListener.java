package at.ac.tuwien.swa.swazam.network.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class JoinListener implements Runnable {

	private int port;
	private PeerListener manager;
	private AtomicBoolean isActive;
	private int portInUse;

	public JoinListener(PeerListener callback, int port) {
		this.manager = callback;
		this.port = port;
		this.portInUse = -1;
		isActive = new AtomicBoolean(true);
	}

	public JoinListener(PeerListener callback) {
		this(callback, 0);
	}

	public void run() {
		try (ServerSocket server = new ServerSocket(port)) {
			portInUse = server.getLocalPort();
			while (isActive.get()) {
				Socket socket = server.accept();
				System.out.println("new peer");
				Peer peer = new Peer(socket);
				manager.register(peer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		isActive.set(false);
	}

	public int getPortInUse() {
		return portInUse;
	}
}
