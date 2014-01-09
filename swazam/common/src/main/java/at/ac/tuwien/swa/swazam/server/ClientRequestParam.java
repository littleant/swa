package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

public class ClientRequestParam implements Serializable {

	private static final long serialVersionUID = 2740278292364600895L;

	private String fingerprint; // TODO change to Fingerprint type
	
	public ClientRequestParam(String fingerprint) {
		super();
		this.fingerprint = fingerprint;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	
}
