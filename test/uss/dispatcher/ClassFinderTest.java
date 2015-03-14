package uss.dispatcher;

import org.junit.Test;

import uss.mapper.dispatch.support.ClassFinder;

public class ClassFinderTest {

	@Test
	public void test() {
		ClassFinder cf = new ClassFinder();
		System.out.println(cf.find("uss"));
	}

}
