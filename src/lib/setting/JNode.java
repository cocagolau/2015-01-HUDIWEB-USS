package lib.setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class JNode {

	Map<String, JNode> childNodes = new HashMap<String, JNode>();
	Map<String, String> childs = new HashMap<String, String>();

	public JNode(JsonReader reader) throws IOException {

		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			try {
				childs.put(key, reader.nextString());
			} catch (IllegalStateException e) {
				JNode node = new JNode(reader);
				childNodes.put(key, node);
			}
		}
		reader.endObject();
	}

	@Override
	public String toString() {
		return "Node [childNodes=" + childNodes + ", childs=" + childs + "]";
	}

	public String get(String key) {
		return childs.get(key);
	}

	public JNode getNode(String key) {
		return childNodes.get(key);
	}

	public String get(String... keys) {
		int length = keys.length;
		if (length == 0)
			return null;
		if (length == 1)
			return get(keys[0]);
		JNode tnode = this;
		for (int i = 0; i < length; i++) {
			if (i == length - 1)
				return tnode.get(keys[i]);
			tnode = tnode.getNode(keys[i]);
		}
		return null;
	}

}
