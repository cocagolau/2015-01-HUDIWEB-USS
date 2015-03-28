package lib.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import lib.setting.Jobject.JMap;

import com.google.gson.stream.JsonReader;

public class Setting {

	static JMap node;

	static {
		String path = JMap.class.getResource("/").getPath();
		try {
			JsonReader reader = new JsonReader(new FileReader(path + "../lib.setting"));
			node = new JMap(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String... keys) {
		return node.get(keys).toString();
	}

}
