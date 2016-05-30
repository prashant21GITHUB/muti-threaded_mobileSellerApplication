package com.wholeseller.client;

import com.wholeseller.inventory.PhoneBrand;
/**
 * 
 * @author bajpai
 * 
 * This is the interface which will be exposed to clients.
 * The implementation is {@link ClientInterfaceImpl.java}.
 * 
 */
public interface ClientInterface {

	double totalDollarsSpentPerClient(int clientId);
	
	int articlesSoldPerBrand(PhoneBrand brand);
	
	void purchaseOrder(int clientId, PhoneBrand brand, int quantity);
}
