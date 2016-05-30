package com.wholeseller.inventory;

/**
 * 
 * @author bajpai
 *
 * To add a new brand and its price, you can make an entry here, and then restart the application again.
 * We have make entry in {@link InventoryConstants.java} and to initialize make modification in 
 * {@link Inventory.init()} method and restart the application.
 */

public enum PhoneBrand {
	NOKIA("Nokia",200),
	SAMSUNG("Samsung", 300),
	LG("LG",150),
	APPLE("Apple", 320),
	WINDOWS("Windows", 250);
	
	private final double price;
	private final String modelName;
	
	private PhoneBrand(String modelName, double price) {
		this.modelName = modelName;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public String getModelName() {
		return modelName;
	}
}
