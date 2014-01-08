package at.ac.tuwien.swa.swazam.network;

public interface NetworkManager {

	public abstract void sendRequest(String request);

	public abstract void disconnect();

}
