package com.wholeseller.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import com.wholeseller.order.Request;

/**
 * 
 * @author bajpai
 * 
 * Its an implementation of {@link IThreadPool.java}
 *
 * This is the threadPool to serve the client requests from multiple clients.
 * The default size of this pool is defined in {@link PoolConstants.java}. You can change the values as per your requirements.
 * 
 * All the requests from one client will be served by that thread which is already having/executing the requests from same client.  
 * If threadPool is shutdown, then further requests will not be entertained, but the previous requests which are already been
 * submitted will be served.
 * 
 * For more details, see {@link PoolThread.java} and {@link PoolRunnable.java}
 */
public class OrderPool implements IThreadPool {

	private int poolSize;
	private volatile boolean started;
	private volatile boolean stopped;
	private final List<PoolThread> totalThreads = new ArrayList<>();
	private final BlockingQueue<PoolThread> availableThreads;
	private Map<Integer, PoolThread> clientThreadMap = new HashMap<>();
	
	private OrderPool() {
		this.poolSize = PoolConstants.DEFAULT_THREAD_POOL_SIZE;
		availableThreads = new LinkedBlockingQueue<>(this.poolSize);
	}
	
	private OrderPool(int size) {
		if(size <= 0) throw new IllegalArgumentException("Pool size must be a non-zero positive number..");
		this.poolSize = size;
		availableThreads = new LinkedBlockingQueue<>(this.poolSize);
	}
	
	public static OrderPool getPool(int size) {
		if(size > PoolConstants.MAXIMUM_THREAD_POOL_SIZE) {
			throw new IllegalArgumentException("Pool size must be less or equal to: "+PoolConstants.MAXIMUM_THREAD_POOL_SIZE);
		}
		return new OrderPool(size);
	}
	
	public static OrderPool getPool() {
		return new OrderPool();
	}
	
	@Override
	public void submitRequest(Request request) {
		if(stopped) {
			System.err.println("Order pool has been shutdown"); return;
		}
		if(!started) {
			init();
		}
		try {
			if (clientThreadMap.containsKey(request.getClientId())) {
				clientThreadMap.get(request.getClientId()).getTaskQueue()
						.put(new PoolRunnable(request));

			} else {
				PoolThread thread = availableThreads.take();
				thread.setClientId(request.getClientId());
				clientThreadMap.put(request.getClientId(), thread);
				thread.getTaskQueue().put(new PoolRunnable(request));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To initialize thread pool.
	 */
	private synchronized void init() {
		if(started) return;
		PoolThread thread;
		CountDownLatch latch = new CountDownLatch(this.poolSize);
		for(int i=0; i< poolSize; i++) {
			thread = new PoolThread(availableThreads, clientThreadMap, "thread-"+i, latch);
			totalThreads.add(thread);
			availableThreads.add(thread);
			thread.start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		started = true;
	}
	
	/**
	 * To shutdown the pool, All the present requests will be served before shutdown. No further requests will be entertained.
	 */
	public void shutDown() {
		for(PoolThread thread : totalThreads) {
			thread.stopThread();
		}
		stopped = true;
	}
	
	public int getPoolSize() {
		return this.poolSize;
	}
	
	public int getAvailableThreads() {
		return this.availableThreads.size();
	}
	
	public boolean isStarted() {
		return started;
	}

	public boolean isStopped() {
		return stopped;
	}
}
