package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class ClientRequestParam implements Serializable {

	private static final long serialVersionUID = 2740278292364600895L;

	private Fingerprint fingerprint; // TODO change to Fingerprint type
	
	public ClientRequestParam(Fingerprint fingerprint) {
		super();
		this.fingerprint = fingerprint;
	}

	public Fingerprint getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(Fingerprint fingerprint) {
		this.fingerprint = fingerprint;
	}
	
}
