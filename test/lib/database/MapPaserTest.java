package lib.database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapPaserTest {

	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "abc");
		map.put("age", 17);
		map.put("birthday", new Date());
		Parser.setObject(Member.class, map);
		System.out.println(map);
	}

}
