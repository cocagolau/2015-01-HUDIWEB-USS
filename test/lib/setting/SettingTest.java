package lib.setting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() throws IOException {
		System.out.println(Setting.node);
		Setting.node.toString();

		System.out.println(Setting.get("database", "password"));
		JMap node = new JMap(Setting.node.toString());
		System.out.println(node);
		List<Object> abc = new ArrayList<Object>();
		abc.add("abc");
		abc.add("abc");
		abc.add("abc");
		JMap node2 = new JMap(node.toString());
		node2.childs.put("abc", abc);
		System.out.println(node2);
		
		JMap node3 = new JMap(node2.toString());
		System.out.println(node3);
	}

}
