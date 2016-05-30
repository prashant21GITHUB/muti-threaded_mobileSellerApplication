package com.wholeseller.inventory;

public class PhoneQuantity {

	private final PhoneBrand brand;
	private int quantity;
	
	public PhoneQuantity(PhoneBrand brand, int quantity){
		this.brand = brand;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public PhoneBrand getBrand() {
		return brand;
	}
}
