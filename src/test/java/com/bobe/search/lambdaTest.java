package com.bobe.search;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.FactoryConfigurationError;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class lambdaTest {
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
	@Test
	public void testLambda(){
		String[] arr = {"wal","cell","java","bools"};
		//1.
		Stream<String> stream = Stream.of(arr);
		//将数组转化为流并且输出流元素
		//stream.forEach(x-> System.out.println(x));
		//2
		String s = stream.filter(x -> x.length() == 3).findFirst().get();
		System.out.println(s);
		
		
	}
	@Test
	public void testLambda_2(){
		String[] arr = {"wal","cell","java","bools"};
		
		Stream<String> stream  = Stream.of(arr);
		
	//	boolean match = stream.anyMatch(((x -> x.equals("wal"))));
	//	System.out.println(match);
	//	stream.forEach(System.out::println);
	//创建线程操作lambda  表达式
	//new Thread(()-> System.out.println("hello world")).start();
		//
	//	Runnable runnable =()->{ System.out.println("hello world");};
	//	runnable.run();
		List<String> list = new ArrayList<>();
		list.add("hello_1");
		list.add("hello");
		list.add("hello_2");
		list.add("hello_3");
		list.add("hello");
		list.add("hello_3");
	}
	
	@Test
	public void testConnectPackage(){
		Map<Integer,Integer> map = new ConcurrentHashMap(20);
		for (int i=0;i<20;i++){
			map.put(i,i+2);
		}
		List<Integer> list =(List)  map.values().stream().map(x->x.intValue()+30).collect(Collectors.toList());
		System.out.println("=======");
		System.out.println(Stream.of(1,2,3,4,5,6).reduce(0,(x,y)->x+y));
		list.stream().forEach(System.out::println);
	}
	
	@Test
	public void testCyclicbarrer(){
	
	 final 	CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
			@Override
			public void run() {
				logger.info("完成信息重点......."+Thread.currentThread().getName());
			}
		});
		
		ExecutorService service  = Executors.newCachedThreadPool();
		//发现使用陷阱,使用固定大小的时候线程数量小于信号量个数时，达到死锁情况，此时方法锁死一直在等待中，
		
		//   通过测试得出下列方案
		// （1）将线程池数量个数大小调制parties以上
		// （2）或者不使用固定大小线程池，换成可缓存的
		//  (3) 或者将固定线程池数量创建的与parties 数量一致
		// （4）这种并发情况下  防止线程池与其他业务线程池共用，使线程池资源全部处在驻停状态  影响其他业务。
		
		/**
		 * CyclicBarrier 线程同步辅助类使用方法及其注意事项
		 * 作用：
		 * CyclicBarrier是一个同步的辅助类，允许一组线程相互之间等待，达到一个共同点，再继续执行。
		 * 注意事项如上：四点（自己总结的不全请谅解）
		 * 使用场景：挺多的哈：
		 */
		
		for (int i=0;i<60;i++){
			service.submit(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(10);
						logger.info("这是一起到的======>>>>>>>"+Thread.currentThread().getName());
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		logger.info("这是完成的信息");
		
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		service.shutdownNow();
		
	}
	
	@Test
	public void testCountDownLatch(){
		
		/**
		 * @see: CountDownLatch
		 * 说明：线程同步辅助类：
		 * 用法：某一个线程等待其他一个或者几个线程一起完成任务结束等待状态。
		 * CountDownLatch是一个同步的辅助类，允许一个或多个线程，等待其他一组线程完成操作，再继续执行。
		 * 在需要等待的线程中调用方法 vCountDownLatch是一个同步的辅助类，允许一个或多个线程，等待其他一组线程完成操作，再继续执行。
		 * 在需要调用的线程中使用 latch.CountDown()方法（闭锁操作）
		 * 注意事项 等待个数一定需要满足指定数量   否则持续等待，当前方法被阻塞,但是线程还是可以被利用的。
		 *   在等待线程中需要调用
		 *
		 *   场景举例：流水线式的工作过程   等所有任务完成了才能进行其他造作（开会，游戏等待等场景）
		 */
		CountDownLatch latch = new CountDownLatch(20
		);
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		
		for (int i=0;i<20;i++){
			
			service.submit(new Runnable() {
				@Override
				public void run() {
					logger.info("当前线程"+Thread.currentThread().getName());
					try {
						latch.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		try {
			latch.await(); //等待其他线程完成操作，即可继续下面任务
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("信息完成");
		
		service.shutdown();
	}
	
	
	public void test(){
		/**
		 * 信号量：线程同步辅助类，主要学习两点：
		 * 信号量就是可以声明多把锁（包括一把锁：此时为互斥信号量）
		 *  一个房间如果只能容纳5个人，多出来的人必须在门外面等着。如何去做呢？一个解决办法就是：
		 *  房间外面挂着五把钥匙，每进去一个人就取走一把钥匙，没有钥匙的不能进入该房间而是在外面等待。
		 *  每出来一个人就把钥匙放回原处以方便别人再次进入。
		 *  常用说明方法：
		 *  acquire():获取信号量，信号量内部计数器减1
		 *  release():释放信号量，信号量内部计数器加1
		 *  tryAcquire():这个方法试图获取信号量，如果能够获取返回true，否则返回false
		 *  信号量控制的线程数量在声明时确定：
		 *  Semaphore semaphore = new Semaphore(3);
		 *  注意事项：
		 *  1、对于信号量声明的临界区，虽然可以控制线程访问的数量，但是不能保证代码块之间是线程安全的
		 *  2、号量也涉及到公平性问题。和锁公平性一样，这里默认是非公平的。可以通过构造器显示声明锁的公平性。
		 *
		 *  应用场景
		 *  流量控制，即控制能够访问的最大线程数。(流量控制可以使用google核心工具包RuteLimiter 临牌桶算法)
		 *
		 *
		 */
		
		Semaphore semaphore = new Semaphore(3);
	}
	
	@Test
	public void testReadWriteLock(){
		/**
		 * ReentrantReadWriteLock 读写锁
		 * 特新：
		 * 获取锁顺序：默认为非公平模式
		 * 当以非公平初始化时，读锁和写锁的获取的顺序是不确定的。
		 * 非公平锁主张竞争获取，可能会延缓一个或多个读或写线程，但是会比公平锁有更高的吞吐量。
		 * 公平模式
		 * 当以公平模式初始化时，线程将会以队列的顺序获取锁。当当前线程释放锁后，
		 * 等待时间最长的写锁线程就会被分配写锁；
		 * 或者有一组读线程组等待时间比写线程长，那么这组读线程组将会被分配读锁。
		 * 可重入
		 * 允许读锁可写锁可重入。写锁可以获得读锁，读锁不能获得写锁。
		 * - 锁降级
		 * 允许写锁降低为读锁
		 * - 中断锁的获取
		 * 在读锁和写锁的获取过程中支持中断
		 * - 支持Condition
		 * 写锁提供Condition实现
		 * - 监控
		 * 提供确定锁是否被持有等辅助方法
		 */
		
	}
	
	
	
	
	
}
