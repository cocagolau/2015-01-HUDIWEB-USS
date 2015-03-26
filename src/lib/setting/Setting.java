package lib.setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class Setting {

	private static Setting setting = new Setting();

	private List<String> set = new ArrayList<String>();
	private Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

	public Setting() {
		String path = Setting.class.getResource("/").getPath();
		set.add("database");
		set.add("defaultType");
		set.add("defaultCondition");

		try {
			JsonReader reader = new JsonReader(new FileReader(path + "../lib.setting"));
			Map<String, String> defaultMap = new HashMap<String, String>();
			map.put("default", defaultMap);
			readSettings(defaultMap, reader);
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Current Path: " + System.getProperty("user.dir"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String get(String map, String type) {
		return setting.map.get(map).get(type);
	}

	@Override
	public String toString() {
		return "Setting [set=" + set + ", map=" + map + "]";
	}

	private void readSettings(Map<String, String> map, JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			if (set.contains(key)) {
				Map<String, String> newMap = new HashMap<String, String>();
				this.map.put(key, newMap);
				readSettings(newMap, reader);
			} else {
				String value = reader.nextString();
				map.put(key, value);
			}
		}
		reader.endObject();
	}

}
