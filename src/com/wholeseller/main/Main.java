package com.wholeseller.main;

import com.wholeseller.client.ClientInterface;
import com.wholeseller.client.ClientInterfaceImpl;
import com.wholeseller.inventory.Inventory;
import com.wholeseller.inventory.PhoneBrand;
/**
 * 
 * @author bajpai
 *
 * To test application.
 * Note: We have to initialize the inventory first for application to work successfully.
 * To initialize inventory, call {@link Inventory.initializeInventory()}.
 * 
 * We can verify in the output that if any thread is serving the request of client 1(say), then if 
 * then the further requests from client 1 will be served by this thread only, provided this thread
 * is already serving/having the request from same cleint( 1 in our case).
 * We can verify by thread-Id and client-Id which will be displayed on Output screen by running
 * main() method.
 * 
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Inventory.initializeInventory(); // Initialize inventory before any purchase
		
		ClientInterface clientInterface = new ClientInterfaceImpl();
		
		clientInterface.purchaseOrder(1, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(1, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(1, PhoneBrand.WINDOWS, 100);
		clientInterface.purchaseOrder(1, PhoneBrand.LG, 100);
		clientInterface.purchaseOrder(7, PhoneBrand.NOKIA, 100);
		clientInterface.purchaseOrder(7, PhoneBrand.NOKIA, 100);
		clientInterface.purchaseOrder(2, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(2, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(2, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(2, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(3, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(3, PhoneBrand.SAMSUNG, 100);
		clientInterface.purchaseOrder(3, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(4, PhoneBrand.APPLE, 100);
		clientInterface.purchaseOrder(4, PhoneBrand.LG, 100);
		clientInterface.purchaseOrder(5, PhoneBrand.WINDOWS, 100);
		clientInterface.purchaseOrder(5, PhoneBrand.WINDOWS, 100);
		
		Thread.sleep(2000);  // orders to fulfilled successfully
		
		System.out.println();
		System.out.println("Articles sold: apple: "+clientInterface.articlesSoldPerBrand(PhoneBrand.APPLE));
		System.out.println("Articles sold: LG: "+clientInterface.articlesSoldPerBrand(PhoneBrand.LG));
		System.out.println("Articles sold: Samsung: "+clientInterface.articlesSoldPerBrand(PhoneBrand.SAMSUNG));
		System.out.println("Articles sold: Nokia: "+clientInterface.articlesSoldPerBrand(PhoneBrand.NOKIA));
		System.out.println("Articles sold: Windows: "+clientInterface.articlesSoldPerBrand(PhoneBrand.WINDOWS));
		
		System.out.println();
		System.out.println("Total dollars spent by client 1: " + clientInterface.totalDollarsSpentPerClient(1));
		System.out.println("Total dollars spent by client 2: " + clientInterface.totalDollarsSpentPerClient(2));
		System.out.println("Total dollars spent by client 3: " + clientInterface.totalDollarsSpentPerClient(3));
		System.out.println("Total dollars spent by client 4: " + clientInterface.totalDollarsSpentPerClient(4));
		System.out.println("Total dollars spent by client 5: " + clientInterface.totalDollarsSpentPerClient(5));
		System.out.println("Total dollars spent by client 7: " + clientInterface.totalDollarsSpentPerClient(7));
	}
}
