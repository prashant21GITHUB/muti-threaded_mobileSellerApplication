package com.wholeseller.record;

/**
 * 
 * @author bajpai
 *
 * Record to save the total dollars spent for a client.
 * 
 */
public class ClientRecord {

	private final int clientId;
	private double dollarsSpent;
	
	public ClientRecord(int clientId) {
		this.clientId = clientId;
	}
	
	public double getDollarsSpent() {
		return dollarsSpent;
	}

	public void setDollarsSpent(double dollarsSpent) {
		this.dollarsSpent = dollarsSpent;
	}

	public int getClientId() {
		return clientId;
	}
}
