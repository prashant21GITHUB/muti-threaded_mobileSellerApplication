package com.wholeseller.inventory;

/**
 * 
 * @author bajpai
 *
 * This exception class is to handle Inventory related exceptions at runtime.
 *
 */
public class InventoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InventoryException(String msg) {
		super(msg);
	}
	
	public InventoryException() {
		super();
	}
}
