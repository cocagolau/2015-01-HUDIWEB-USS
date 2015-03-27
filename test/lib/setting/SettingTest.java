package lib.setting;


import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		System.out.println(Setting.get("controllerPath"));
		System.out.println(Setting.get("database", "url"));
	}

}
