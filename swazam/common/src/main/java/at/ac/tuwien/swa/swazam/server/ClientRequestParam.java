package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class ClientRequestParam implements Serializable {

	private static final long serialVersionUID = 2740278292364600895L;

	private Fingerprint fingerprint;
	private long userId;
	
	public ClientRequestParam(long userId, Fingerprint fingerprint) {
		super();
		this.fingerprint = fingerprint;
		this.userId = userId;
	}

	public Fingerprint getFingerprint() {
		return fingerprint;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setFingerprint(Fingerprint fingerprint) {
		this.fingerprint = fingerprint;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
