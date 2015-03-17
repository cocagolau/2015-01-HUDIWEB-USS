package lib.database;

import java.util.Date;

import org.junit.Test;

public class SqlAndParamsTest {

	@Test
	public void test() {
		Member a = new Member();
		a.setAge(18);
		a.setBirthday(new Date());
		SqlAndParams sap = new SqlAndParams(a);
		System.out.println(sap);
		System.out.println(sap.getInsertString("member"));
		System.out.println(sap.getUpdateString("member", "where id=?"));
	}

}
