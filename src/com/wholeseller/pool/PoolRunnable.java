package com.wholeseller.pool;

import com.wholeseller.inventory.Inventory;
import com.wholeseller.order.Request;

/**
 * 
 * @author bajpai
 *
 * This class is runnable for {@link PoolThread.java}.
 */
public class PoolRunnable implements Runnable {

	private final Request request;
	
	public PoolRunnable(Request request) {
		this.request = request;
	}
	
	@Override
	public void run() {
		Inventory inventory = Inventory.getInventory();
		inventory.updateInventory(request);
	}

}
