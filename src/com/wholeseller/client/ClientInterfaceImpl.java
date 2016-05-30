package com.wholeseller.client;

import com.wholeseller.inventory.PhoneBrand;
import com.wholeseller.order.PhoneOrderImpl;
import com.wholeseller.order.PhoneOrderInterface;
/**
 * 
 * @author bajpai
 *
 * This is an implementation of {@link ClientInterface.java}.
 * 
 */
public class ClientInterfaceImpl implements ClientInterface{
	
	private PhoneOrderInterface phoneOrderInterface;
	
	
	@Override
	public void purchaseOrder(int clientId, PhoneBrand brand, int quantity) {
		phoneOrderInterface.requestOrder(clientId, brand, quantity);
	}
	
	public ClientInterfaceImpl() {
		phoneOrderInterface = new PhoneOrderImpl();
	}

	@Override
	public double totalDollarsSpentPerClient(int clientId) {
		return phoneOrderInterface.totalDollarsSpentPerClient(clientId);
	}

	@Override
	public int articlesSoldPerBrand(PhoneBrand brand) {
		return phoneOrderInterface.articlesSoldPerBrand(brand);
	}
}
