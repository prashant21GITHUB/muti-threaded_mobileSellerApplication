package com.wholeseller.order;

import com.wholeseller.inventory.PhoneBrand;
/**
 * 
 * @author bajpai
 *
 * Request object will be passed to {@link OrderPool.java}
 */
public class Request {

	private final int clientId;
	private final PhoneBrand brand;
	private final int quantity;
	
	public Request(int clientId, PhoneBrand brand, int quantity){
		this.clientId = clientId;
		this.brand = brand;
		this.quantity = quantity;
	}

	public int getClientId() {
		return clientId;
	}

	public PhoneBrand getBrand() {
		return brand;
	}

	public int getQuantity() {
		return quantity;
	}
}
