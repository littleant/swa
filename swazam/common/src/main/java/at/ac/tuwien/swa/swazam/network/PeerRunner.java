package at.ac.tuwien.swa.swazam.network;

import java.io.IOException;

import at.ac.tuwien.swa.swazam.network.p2p.P2PNode;

public class PeerRunner {

	public static void main(String[] args) throws IOException {
		NetworkNode node = new P2PNode("localhost", 8888);
		System.in.read();
		node.disconnect();
	}
}
