package de.jotschi.acoustid.client;

public class Fingerprint {

	private String fingerprint;
	
	public Fingerprint() {
		
	}

	public Fingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	
	public void setString(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	@Override
	public String toString() {
		return fingerprint;
	}
}
