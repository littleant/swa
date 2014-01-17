package at.ac.tuwien.swa.swazam.server;

import at.ac.tuwien.swa.swazam.server.exception.SongNotFoundException;

public interface MessageListener {
	public PeerMessage receiveMessage(String messageSelector) throws SongNotFoundException;
}
