package com.wholeseller.pool;

import com.wholeseller.order.Request;

/**
 * 
 * @author bajpai
 * 
 * Implementation is {@link OrderPool.java}
 *
 */
public interface IThreadPool {
	
	void submitRequest(Request request);
}
