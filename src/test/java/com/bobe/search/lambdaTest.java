package com.bobe.search;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class lambdaTest {
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
	
}
