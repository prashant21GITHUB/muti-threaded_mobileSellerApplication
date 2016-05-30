package com.wholeseller.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wholeseller.inventory.Inventory;
import com.wholeseller.inventory.PhoneBrand;

public class ClientInterfaceImplTest {

	private ClientInterface clientInterface;
	
	@Before
	public void setUp() {
		clientInterface = new ClientInterfaceImpl();
		Inventory.initializeInventory();
		Inventory.getInventory().reset();
	}
	
	@Test
	public void test_purchaseOrder() throws InterruptedException {
		clientInterface.purchaseOrder(1, PhoneBrand.LG, 200);
		Thread.sleep(500);
		Inventory inventory = Inventory.getInventory();
		Assert.assertTrue(PhoneBrand.LG.getPrice() * 200 == inventory.getTotalDollarsSpent(1));
	}
	
	@Test
	public void test_articlesSoldPerBrand() throws InterruptedException {
		clientInterface.purchaseOrder(1, PhoneBrand.LG, 200);
		Thread.sleep(500);
		Assert.assertTrue(200 == clientInterface.articlesSoldPerBrand(PhoneBrand.LG));
	}
	
	@Test
	public void test_totalDollarsPerClient() throws InterruptedException {
		clientInterface.purchaseOrder(2, PhoneBrand.SAMSUNG, 300);
		Thread.sleep(500);
		Assert.assertTrue(PhoneBrand.SAMSUNG.getPrice() * 300 == clientInterface.totalDollarsSpentPerClient(2));
	}

}
