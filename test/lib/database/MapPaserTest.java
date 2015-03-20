package lib.database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapPaserTest {

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member_name", "abc");
		map.put("Member_age", 17);
		map.put("Member_birthday", new Date());
		Member m = Parser.setObject(Member.class, map);
		System.out.println(map);
		System.out.println(m);
	}

}
