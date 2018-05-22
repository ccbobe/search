package com.bobe.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Test
	public void testlambda(){
		String[] arr = { "program", "creek", "is", "a", "java", "site" };
		Arrays.sort(arr, (String m, String n) -> {
			if (m.length() > n.length())
				return -1;
			else
				return 0;
		});
		System.out.println(Arrays.toString(arr));
	}
	
	
	
}
