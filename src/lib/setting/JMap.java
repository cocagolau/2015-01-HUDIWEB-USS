package lib.setting;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JMap {

	Map<Object, Object> childs = new HashMap<Object, Object>();

	public JMap(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			if (reader.peek() == JsonToken.NAME) {
				String key = reader.nextName();
				JsonToken peek = reader.peek();
				if (peek == JsonToken.STRING)
					childs.put(key, reader.nextString());
				else if (peek == JsonToken.NUMBER)
					childs.put(key, reader.nextDouble());
				else if (peek == JsonToken.BOOLEAN)
					childs.put(key, reader.nextBoolean());
				else if (peek == JsonToken.NULL) {
					reader.nextNull();
					childs.put(key, null);
				} else if (peek == JsonToken.BEGIN_OBJECT) {
					JMap node = new JMap(reader);
					childs.put(key, node);
				} else if (peek == JsonToken.BEGIN_ARRAY) {
					childs.put(key, array(reader));
				}
			}
		}
		reader.endObject();
	}

	private List<Object> array(JsonReader reader) throws IOException {
		List<Object> result = new ArrayList<Object>();
		reader.beginArray();
		while (reader.hasNext()) {
			JsonToken peek = reader.peek();
			if (peek == JsonToken.STRING)
				result.add(reader.nextString());
			else if (peek == JsonToken.NUMBER)
				result.add(reader.nextDouble());
			else if (peek == JsonToken.BOOLEAN)
				result.add(reader.nextBoolean());
			else if (peek == JsonToken.NULL) {
				reader.nextNull();
				result.add(null);
			} else if (peek == JsonToken.BEGIN_OBJECT) {
				JMap node = new JMap(reader);
				result.add(node);
			} else if (peek == JsonToken.BEGIN_ARRAY) {
				result.add(array(reader));
			}
		}
		reader.endArray();
		return result;
	}

	public JMap(String string) throws IOException {
		this(new JsonReader(new StringReader(string)));
	}

	public static Map<Object, Object> toMap(JMap jnode) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> childNodes = jnode.getChilds();
		if (!childNodes.isEmpty()) {
			for (Map.Entry<Object, Object> entry : childNodes.entrySet()) {
				if (entry.getValue().getClass().equals(JMap.class))
					result.put(entry.getKey(), toMap((JMap) entry.getValue()));
				else
					result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(toMap(this));
	}

	private Map<Object, Object> getChilds() {
		return childs;
	}

	public Object get(String key) {
		return childs.get(key);
	}

	public JMap getNode(String key) {
		return (JMap) childs.get(key);
	}

	public Object get(String... keys) {
		int length = keys.length;
		if (length == 0)
			return null;
		if (length == 1)
			return get(keys[0]);
		JMap tnode = this;
		for (int i = 0; i < length; i++) {
			if (i == length - 1)
				return tnode.get(keys[i]);
			tnode = tnode.getNode(keys[i]);
		}
		return null;
	}

}
