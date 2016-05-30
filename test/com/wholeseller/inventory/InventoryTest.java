package com.wholeseller.inventory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wholeseller.order.Request;

public class InventoryTest {

	private Inventory inventory;
	
	@Before
	public void setUp() {
		inventory = Inventory.getInventory();
	}
	
	@Test
	public void test_For_Singleton() {
		Inventory inventoryOther = Inventory.getInventory();
		Assert.assertEquals(inventory.hashCode(), inventoryOther.hashCode());
	}
	
	@Test
	public void test_IfInventoryIsInitialized() {
		Inventory.initializeInventory();
		Assert.assertNotNull(inventory);
		Assert.assertTrue(Inventory.isInitialized());
	}
	
	@Test
	public void test_updateInventory() {
		inventory.reset();
		Assert.assertEquals(0, inventory.articlesSoldPerBrand(PhoneBrand.APPLE));
		inventory.updateInventory(new Request(1, PhoneBrand.APPLE, 100));
		Assert.assertEquals(100, inventory.articlesSoldPerBrand(PhoneBrand.APPLE));
	}
	
	@Test
	public void test_DollarsSpentPerClient() {
		inventory.reset();
		Assert.assertTrue(0 == inventory.getTotalDollarsSpent(1));
		inventory.updateInventory(new Request(1, PhoneBrand.SAMSUNG, 200));
		Assert.assertTrue(60000 == inventory.getTotalDollarsSpent(1));
	}
	
	@Test
	public void test_When_Not_enough_models() {
		inventory.reset();
		Assert.assertTrue(0 == inventory.getTotalDollarsSpent(1));
		inventory.updateInventory(new Request(1, PhoneBrand.SAMSUNG, 200));
		Assert.assertTrue(60000 == inventory.getTotalDollarsSpent(1));
		Assert.assertTrue(200 == inventory.articlesSoldPerBrand(PhoneBrand.SAMSUNG));
		inventory.updateInventory(new Request(1, PhoneBrand.SAMSUNG, 20000));
		Assert.assertTrue(60000 == inventory.getTotalDollarsSpent(1));
		Assert.assertTrue(200 == inventory.articlesSoldPerBrand(PhoneBrand.SAMSUNG));
	}

}
