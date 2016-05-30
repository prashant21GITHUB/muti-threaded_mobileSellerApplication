package com.wholeseller.order;

import  org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wholeseller.inventory.Inventory;
import com.wholeseller.inventory.PhoneBrand;

public class PhoneOrderImplTest {

	private PhoneOrderInterface phoneOrderInterface;
	
	@Before
	public void setUp() {
		phoneOrderInterface = new PhoneOrderImpl();
		Inventory.initializeInventory();
	}

	@Test
	public void test_requestOrder() throws InterruptedException {
		Inventory inventory = Inventory.getInventory();
		inventory.reset();
		phoneOrderInterface.requestOrder(1, PhoneBrand.APPLE, 200);
		Thread.sleep(1000);
		Assert.assertTrue(PhoneBrand.APPLE.getPrice() * 200 ==  inventory.getTotalDollarsSpent(1));
	}

}
