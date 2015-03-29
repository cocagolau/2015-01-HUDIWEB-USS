package lib.database;

import java.util.Date;

import lib.database.exception.RegexNotMatches;
import lib.database.sql.KeyParams;

import org.junit.Test;

public class SqlAndParamsTest {

	@Test
	public void test() throws RegexNotMatches {
		Member a = new Member();
		a.setAge(18);
		a.setBirthday(new Date());
		KeyParams sap = new KeyParams(a);
		System.out.println(sap);
	}

}
