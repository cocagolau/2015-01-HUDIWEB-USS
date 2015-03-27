package lib.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.stream.JsonReader;

public class Setting {

	private static JNode node;

	static {
		String path = JNode.class.getResource("/").getPath();
		try {
			JsonReader reader = new JsonReader(new FileReader(path + "../lib.setting"));
			node = new JNode(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String... keys) {
		return node.get(keys);
	}

}
