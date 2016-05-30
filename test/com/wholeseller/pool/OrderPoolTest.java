package com.wholeseller.pool;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wholeseller.inventory.Inventory;
import com.wholeseller.inventory.PhoneBrand;
import com.wholeseller.order.Request;

public class OrderPoolTest {
	
	private OrderPool pool;

	@Before
	public void setUp() throws InterruptedException {
		Inventory.initializeInventory();
		Thread.sleep(100);
		Inventory.getInventory().reset();
	}

	@Test
	public void test_PoolSize() {
		pool = OrderPool.getPool();
		Assert.assertEquals(PoolConstants.DEFAULT_THREAD_POOL_SIZE, pool.getPoolSize());
		pool = OrderPool.getPool(11);
		Assert.assertEquals(11, pool.getPoolSize());
	}
	
	@Test
	public void test_whetherPoolIsStared() {
		pool = OrderPool.getPool(11);
		Assert.assertFalse(pool.isStarted());
	}
	
	@Test
	public void test_submitRequest_singleClient() {
		pool = OrderPool.getPool(11);
		Assert.assertFalse(pool.isStarted());
		pool.submitRequest(new Request(1, PhoneBrand.SAMSUNG, 100));
		pool.submitRequest(new Request(1, PhoneBrand.APPLE, 100));
		pool.submitRequest(new Request(1, PhoneBrand.SAMSUNG, 200));
		Assert.assertTrue(pool.isStarted());
		Assert.assertTrue(10 == pool.getAvailableThreads());
	}
	
	@Test
	public void test_submitRequest_multipleClients() {
		pool = OrderPool.getPool(11);
		Assert.assertFalse(pool.isStarted());
		pool.submitRequest(new Request(1, PhoneBrand.SAMSUNG, 100));
		pool.submitRequest(new Request(2, PhoneBrand.APPLE, 100));
		pool.submitRequest(new Request(3, PhoneBrand.SAMSUNG, 200));
		pool.submitRequest(new Request(1, PhoneBrand.SAMSUNG, 100));
		pool.submitRequest(new Request(2, PhoneBrand.APPLE, 100));
		pool.submitRequest(new Request(3, PhoneBrand.SAMSUNG, 200));
		pool.submitRequest(new Request(2, PhoneBrand.SAMSUNG, 100));
		pool.submitRequest(new Request(1, PhoneBrand.APPLE, 100));
		pool.submitRequest(new Request(3, PhoneBrand.SAMSUNG, 200));
		Assert.assertTrue(pool.isStarted());
		Assert.assertTrue(8 == pool.getAvailableThreads());
	}

}
