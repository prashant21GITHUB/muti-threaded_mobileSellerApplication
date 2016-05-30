package com.wholeseller.pool;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author bajpai
 *
 * This is the thread which will execute the client requests. This thread will wait on defined taskQueue to take the client requests.
 * The requests from the same client id will be served by this thread only provided :
 *   1- This thread is already executing the request for same client.
 *   2- or The task queue is already having some requests for the same client ID.
 *  
 *  At any moment, the taskQueue will contain the requests from one client only.
 *  When all requests will be served and the taskQueue will be empty, then this thread will add itself in the 
 *  {@link OrderPool.availableThreads} list, to serve the request from other clients and the clientId will be then set to ID of 
 *  that client.
 *  
 */
public class PoolThread extends Thread {

	private int clientId;
	private final BlockingQueue<Runnable> taskQueue;
	private final BlockingQueue<PoolThread> availableThreads;
	private final Map<Integer, PoolThread> clientThreadMap;
	private volatile boolean stopped;
	private CountDownLatch latch;

	/**
	 * 
	 * @param availableThreads - This is the passed by {@link OrderPool.java}. This thread will add itself in it after serving all the 
	 *            requests present in its taskQueue, to take further requests, from other clients.
	 * @param clientThreadMap - This is passed by {@link OrderPool.java} so that once all the requests from same client are served.
	 *            The mapping between this thread and this clientId can be removed, So that this thread can serve the requests 
	 *            from other clients by adding itself in {@link OrderPool.availableThreads}.
	 * @param name   - This is name of thread.
	 * @param latch - This latch is to ensure that before starting OrderPool, all the threads should be up. 
	 * 
	 */
	public PoolThread(BlockingQueue<PoolThread> availableThreads, Map<Integer, PoolThread> clientThreadMap, String name, CountDownLatch latch) {
		this.taskQueue = new LinkedBlockingQueue<>(PoolConstants.MAXIMUM_REQUESTS_AT_A_TIME);
		this.availableThreads = availableThreads;
		this.clientThreadMap = clientThreadMap;
		setName(name);
		this.latch = latch;
	}

	public void run() {
		latch.countDown();
		while (!stopped) {
			try {
				taskQueue.take().run();
				if(taskQueue.peek() == null) {
					availableThreads.add(this);
					clientThreadMap.remove(clientId);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public BlockingQueue<Runnable> getTaskQueue() {
		return taskQueue;
	}
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	public void stopThread() {
		this.stopped = true;
		this.interrupt();
	}
}
