package lib.setting;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JMap implements JObject{

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
					JArray node = new JArray(reader);
					childs.put(key, node);
				}
			}
		}
		reader.endObject();
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
				else if (entry.getValue().getClass().equals(JArray.class))
					result.put(entry.getKey(), JArray.toArray((JArray) entry.getValue()));
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

	@Override
	public Object get(String key) {
		return childs.get(key);
	}

	@Override
	public JObject getNode(String key) {
		return (JObject) childs.get(key);
	}

	@Override
	public Object get(String... keys) {
		int length = keys.length;
		if (length == 0)
			return null;
		if (length == 1)
			return get(keys[0]);
		JObject tnode = this;
		for (int i = 0; i < length; i++) {
			if (i == length - 1)
				return tnode.get(keys[i]);
			tnode = tnode.getNode(keys[i]);
		}
		return null;
	}

}
