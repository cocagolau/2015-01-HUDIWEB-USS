package lib.setting.Jobject;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JArray implements JObject{

	List<Object> childs = new ArrayList<Object>();

	public JArray(JsonReader reader) throws IOException {
		reader.beginArray();
		while (reader.hasNext()) {
			JsonToken peek = reader.peek();
			if (peek == JsonToken.STRING)
				childs.add(reader.nextString());
			else if (peek == JsonToken.NUMBER)
				childs.add(reader.nextDouble());
			else if (peek == JsonToken.BOOLEAN)
				childs.add(reader.nextBoolean());
			else if (peek == JsonToken.NULL) {
				reader.nextNull();
				childs.add(null);
			} else if (peek == JsonToken.BEGIN_OBJECT) {
				JMap node = new JMap(reader);
				childs.add(node);
			} else if (peek == JsonToken.BEGIN_ARRAY) {
				JArray node = new JArray(reader);
				childs.add(node);
			}
		}
		reader.endArray();
	}

	public JArray(String string) throws IOException {
		this(new JsonReader(new StringReader(string)));
	}

	public static List<Object> toArray(JArray jnode) {
		List<Object> result = new ArrayList<Object>();
		List<Object> childNodes = jnode.getChilds();
		if (!childNodes.isEmpty()) {
			childNodes.forEach(childNode ->{
				if (childNode.getClass().equals(JMap.class))
					result.add(JMap.toMap((JMap) childNode));
				else if (childNode.getClass().equals(JArray.class))
					result.add(toArray((JArray) childNode));
				else
					result.add(childNode);
			});
		}
		return result;
	}

	private List<Object> getChilds() {
		return childs;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(toArray(this));
	}
	
	@Override
	public Object get(String key) {
		return childs.get(Integer.parseInt(key));
	}

	@Override
	public JObject getNode(String key) {
		return (JObject) childs.get(Integer.parseInt(key));
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
	
	public void add(Object obj){
		childs.add(obj);
	}

}
