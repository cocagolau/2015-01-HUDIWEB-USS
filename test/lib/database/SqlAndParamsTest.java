package lib.database;

import java.util.Date;

import org.junit.Test;

public class SqlAndParamsTest {

	@Test
	public void test() {
		Member a = new Member();
		a.setAge(18);
		a.setBirthday(new Date());
		SqlParams sap = new SqlParams(a);
		System.out.println(sap);
	}

}
