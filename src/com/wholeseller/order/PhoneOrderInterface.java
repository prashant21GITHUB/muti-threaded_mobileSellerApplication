package com.wholeseller.order;

import com.wholeseller.inventory.PhoneBrand;

/**
 * 
 * @author bajpai
 *
 *To process requests from clients, Implementation is {@link PhoneOrderImpl.java}
 *
 */
public interface PhoneOrderInterface {

	void requestOrder(int clientId, PhoneBrand brand, int quantity);
	
	double totalDollarsSpentPerClient(int clientId);
	
	int articlesSoldPerBrand(PhoneBrand brand);
}
