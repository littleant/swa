package at.ac.tuwien.swa.swazam.server;

import java.io.Serializable;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class PeerRequest implements Serializable {

	private static final long serialVersionUID = -1019779291265136162L;

	private String requestIdentifier;
	private Fingerprint fingerprint;
	
	public PeerRequest(String requestIdentifier, Fingerprint fingerprint) {
		super();
		this.requestIdentifier = requestIdentifier;
		this.fingerprint = fingerprint;
	}
	
	public String getRequestIdentifier() {
		return requestIdentifier;
	}
	public Fingerprint getFingerprint() {
		return fingerprint;
	}
	public void setRequestIdentifier(String requestIdentifier) {
		this.requestIdentifier = requestIdentifier;
	}
	public void setFingerprint(Fingerprint fingerprint) {
		this.fingerprint = fingerprint;
	}
}
