/**
 * 
 */
package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beyond.common.utils.DozerHelper;

/**
 * @author liyue
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "applicationContext.xml" })
public class TestDozer {
	@Autowired
	private DozerHelper dozerHelper;

	@Test
	public void test() {
		A a = new A();
		a.setName("leixiaoliang");
		a.setTitle("zhouzhixing");
		a.setValue("SX");
		;
		B b = dozerHelper.convert(a, B.class);
		System.out.println(b);
	}
}
