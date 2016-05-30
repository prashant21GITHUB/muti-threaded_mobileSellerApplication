package com.wholeseller.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wholeseller.order.Request;
import com.wholeseller.record.ClientRecord;

/**
 * 
 * @author bajpai
 *
 * This is a singleton class.
 * 
 * This class is designed to maintain the inventory, Before taking any client request the inventory must be successfully
 * initialized by calling {@link Inventory.initializeInventory()} method.
 * 
 * Here the default quantity of Items are defined in {@link InventoryConstants.java} file. You can change the default quantity
 * in mentioned java file.
 * 
 */
public class Inventory {

	private static Map<PhoneBrand, PhoneQuantity> phoneModels;
	private static Inventory inventory;
	private static Map<PhoneBrand, Integer> articlesSoldPerBrand;
	private static Map<Integer, ClientRecord> dollarsPerClient;
	private static boolean initialized;
	
	private Inventory(){
		phoneModels = new  ConcurrentHashMap<>();
		articlesSoldPerBrand = new ConcurrentHashMap<>();
		dollarsPerClient = new ConcurrentHashMap<>();
	}
	
	public static Inventory getInventory() {
		if(inventory == null) {
			synchronized(Inventory.class) {
				if(inventory == null) {
					inventory = new Inventory();
					init();
				}
			}
		}
		return inventory;
	}

	/**
	 * The default inventory quantity is 5000 for each item and defined in {@link InventoryConstants.java} file.
	 * You can modify it and again reset the inventory by {@link reset()} method before taking further client requests.
	 */
	public void updateInventory(Request request) {
		PhoneBrand brand = request.getBrand();
		synchronized(phoneModels.get(brand)) {
			if(isEnoughQuantity(request, brand)) {
				updateInventoryArticlesQuantity(request, brand);
				updateClientRecord(request.getClientId(), request.getQuantity() * brand.getPrice());
				System.out.println("Request Success, by " + Thread.currentThread().getName() + " client: "
						+ request.getClientId() + ", Brand: " + brand
						+ ", Quantity: " + request.getQuantity());
			}
		}
		
	}

	private boolean isEnoughQuantity(Request request, PhoneBrand brand) {
		try {
			if (phoneModels.get(brand).getQuantity() < request.getQuantity()) {
				throw new InventoryException("Request Failed, by " + Thread.currentThread().getName() + " client: "
						+ request.getClientId() + ", Brand: " + brand
						+ ", Quantity: " + request.getQuantity()
						+ ", Not Enough quantity present in inventory.");
			}
		} catch (InventoryException ex) {
			System.err.println(ex);
			return false;
		}
		return true;
	}

	private void updateInventoryArticlesQuantity(Request request, PhoneBrand brand) {
		int left = phoneModels.get(brand).getQuantity() - request.getQuantity();
		phoneModels.get(brand).setQuantity(left);
		synchronized (articlesSoldPerBrand) {
			articlesSoldPerBrand.put(brand,
					(articlesSoldPerBrand.get(brand) == null ? 0
							: articlesSoldPerBrand.get(brand)) + request.getQuantity());
		}
	}

	private void updateClientRecord(int clientId, double amount) {
		if (dollarsPerClient.get(clientId) == null) {
			dollarsPerClient.put(clientId, new ClientRecord(clientId));
		}
		dollarsPerClient.get(clientId).setDollarsSpent(dollarsPerClient.get(clientId).getDollarsSpent() + amount);
	}

	/**
	 * Initialize the inventory with default quantity per brand defined in {@link InventoryConstants.java} file.
	 */
	private static void init() {
		phoneModels.put(PhoneBrand.APPLE, new PhoneQuantity(PhoneBrand.APPLE, InventoryConstants.APPLE_QUANTITY));
		phoneModels.put(PhoneBrand.SAMSUNG, new PhoneQuantity(PhoneBrand.SAMSUNG, InventoryConstants.SAMSUNG_QUANTITY));
		phoneModels.put(PhoneBrand.LG, new PhoneQuantity(PhoneBrand.LG, InventoryConstants.LG_QUANTITY));
		phoneModels.put(PhoneBrand.NOKIA, new PhoneQuantity(PhoneBrand.NOKIA, InventoryConstants.NOKIA_QUANTITY));
		phoneModels.put(PhoneBrand.WINDOWS, new PhoneQuantity(PhoneBrand.WINDOWS, InventoryConstants.WINDOWS_QUANTITY));
	}
	
	public int articlesSoldPerBrand(PhoneBrand brand) {
		return articlesSoldPerBrand.get(brand) == null? 0 : articlesSoldPerBrand.get(brand);
	}
	
	public double getTotalDollarsSpent(int clientId) {
		return dollarsPerClient.get(clientId) == null? 0.0 : dollarsPerClient.get(clientId).getDollarsSpent();
	}
	
	public static void initializeInventory() {
		getInventory();
		initialized = true;
	}

	public static boolean isInitialized() {
		return initialized;
	}
	
	/**
	 * This method must be called if you modifying the default items quantity defined in {@link InventoryConstants.java} file.
	 */
	public void reset() {
		init();
		articlesSoldPerBrand.clear();
		dollarsPerClient.clear();
	}
}
