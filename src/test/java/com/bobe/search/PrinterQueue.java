package com.bobe.search;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterQueue {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Semaphore semaphore;
	private Lock pringlock;
	
	private boolean print[];
	
	public PrinterQueue() {
		this.semaphore = new Semaphore(3); //创建三个信号量
		this.pringlock = new ReentrantLock(); //可重入锁
		this.print = new boolean[3];
		
		for (int i=0;i<3;i++){
			print[i] = true; //设置打印服务初始化状态为可用状态
		}
	}
	
	public void printJob(Object document){//打印服务
		try{
			semaphore.acquire();//获取信号量，如果信号量为0，则线程阻塞，如果答应0，则信号量-1
			int assignedPrinter = getPrinter();
			long duration = (long)(Math.random()*10);
			System.out.printf("%s:PrintQueue:Printing a Job in Printer %d during %d seconds\r\n"
					,Thread.currentThread().getName(),assignedPrinter,duration);
			TimeUnit.SECONDS.sleep(duration);
			print[assignedPrinter] = true;
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally {
			semaphore.release();//信号量+1
		}
	}
	
	private int getPrinter(){  //线程不是安全的，所以需要线程同步信息
		int ret = -1;
		try{
			pringlock.lock();
			for(int i = 0; i < print.length;i++){
				if(print[i]){
					ret = i;
					print[i] = false;
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pringlock.unlock();
		}
		return ret;
	}
	
	@Test
	public void testPrinter(){
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		PrinterQueue printQueue = new PrinterQueue();
		
		CountDownLatch loatch = new CountDownLatch(60);
		for (int i=0;i<70;i++){
			
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					printQueue.printJob("hello");
					loatch.countDown();
					logger.info("当前线程信息为"+Thread.currentThread().getName());
				}
			});
		}
		
		try {
			loatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//结合CountDownLatch 完成测试信息 ok （接口限流就可以采用这种手段）
		executorService.shutdown();
	}
	@Test
	public void testSemaphore(){
	    ExecutorService executorService = Executors.newFixedThreadPool(1);
	    Semaphore semaphore = new Semaphore(4);
	    Lock lock = new ReentrantLock();
	    CountDownLatch latch = new CountDownLatch(60);
		for (int i = 0; i < 60; i++) {
		    executorService.submit(new Runnable() {
			    @Override
			    public void run() {
				
				    try {
					    latch.countDown();
				        lock.lock();
					    semaphore.acquire(); //获取信号量，没有获取到需要阻塞
					    Thread.sleep((long)Math.random()*100000);
					    logger.info("sssss======"+semaphore.availablePermits());
					    logger.info(",,,,,"+semaphore.drainPermits());
					    semaphore.release();//释放信号量
					    logger.info("当前线程信息为："+Thread.currentThread().getName()+"=======<<<<<<");
				    } catch (InterruptedException e) {
					    e.printStackTrace();
				    }finally {
				        lock.unlock();
				    }
			    }
		    });
			
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		logger.info("QQ"+semaphore.availablePermits()+"AA"+semaphore.drainPermits());
	}
}
