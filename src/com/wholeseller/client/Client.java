package com.wholeseller.client;

/**
 * 
 * @author bajpai
 * 
 * Class to define a client. There may more information in future.
 * 
 */
public class Client {

	private int clientid;
	private String name;
	
	public Client(int clientId, String name) {
		this.clientid = clientId;
		this.name = name;
	}

	public int getClientid() {
		return clientid;
	}

	public void setClientid(int clientid) {
		this.clientid = clientid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
