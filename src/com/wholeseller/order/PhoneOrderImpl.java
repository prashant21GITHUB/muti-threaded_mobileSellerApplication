package com.wholeseller.order;

import com.wholeseller.inventory.Inventory;
import com.wholeseller.inventory.PhoneBrand;
import com.wholeseller.pool.OrderPool;

/**
 * 
 * @author bajpai
 *
 *Its an implementation of {@link PhoneOrderInterface.java}
 *
 */
public class PhoneOrderImpl implements PhoneOrderInterface {
	
	private OrderPool orderPool;
	
	public PhoneOrderImpl(){
		orderPool = OrderPool.getPool();
		Inventory.initializeInventory();
	}
	
	@Override
	public void requestOrder(int clientId, PhoneBrand brand, int quantity) {
		if(!Inventory.isInitialized()) {
			System.err.println("Please initialize inventory properly, by calling Inventory.initializeInventory() before making any request.");
			return;
		}
		orderPool.submitRequest(new Request(clientId, brand, quantity));
	}

	@Override
	public double totalDollarsSpentPerClient(int clientId) {
		return Inventory.getInventory().getTotalDollarsSpent(clientId);
	}

	@Override
	public int articlesSoldPerBrand(PhoneBrand brand) {
		return Inventory.getInventory().articlesSoldPerBrand(brand);
	}
	
}
