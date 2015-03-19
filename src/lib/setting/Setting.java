package lib.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class Setting {

	public static final String PASSWORD = "password";
	public static final String ID = "id";
	public static final String URL = "url";

	private static Setting setting = new Setting();

	private Map<String, String> map;

	private Setting() {
		String path = Setting.class.getResource("/").getPath();
		map = new HashMap<String, String>();
		try {
			JsonReader reader = new JsonReader(new FileReader(path + "../lib.setting"));
			readDBSettings(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String get(String type) {
		return setting.map.get(type);
	}

	private void readDBSettings(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			String value = reader.nextString();
			map.put(key, value);
		}
		reader.endObject();
	}

}
