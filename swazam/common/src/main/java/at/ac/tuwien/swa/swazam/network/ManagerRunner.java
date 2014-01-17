package at.ac.tuwien.swa.swazam.network;

import java.io.IOException;
import java.util.Scanner;

import at.ac.tuwien.swa.swazam.network.p2p.P2PManager;
import at.ac.tuwien.swa.swazam.server.PeerRequest;

public class ManagerRunner {
	public static void main(String[] args) throws IOException {
		NetworkManager manager = new P2PManager(P2PManager.DEFAULT_PORT);
		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNextLine()) {
				String input = scanner.next();

				if ("q".equals(input)) {
					break;
				}
				PeerRequest req = new PeerRequest(input, null);
				manager.sendRequest(req);
			}
		}
		manager.disconnect();
	}
}
