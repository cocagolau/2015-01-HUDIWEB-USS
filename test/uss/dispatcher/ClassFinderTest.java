package uss.dispatcher;

import lib.mapping.dispatch.support.ClassFinder;

import org.junit.Test;

public class ClassFinderTest {

	@Test
	public void test() {
		ClassFinder cf = new ClassFinder();
		System.out.println(cf.find("uss"));
	}

}
