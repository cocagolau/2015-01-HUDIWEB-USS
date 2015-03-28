package lib.mapping.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import lib.mapping.http.Http;
import lib.setting.Setting;

public class Jsp implements View {

	private String jspFileName;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public Jsp() {
	}

	public void setJspFileName(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public Jsp(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public void put(String key, Object obj) {
		attributes.put(key, obj);
	}
	
	public Object get(String key) {
		return attributes.get(key);
	}

	public void render(Http http) {
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
		    http.setAttribute(entry.getKey(), entry.getValue());
		}
		try {
			http.forword(Setting.get("jspPath") + jspFileName);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public String getJspFileName() {
		return jspFileName;
	}

	@Override
	public String toString() {
		return "Jsp [jspFileName=" + jspFileName + ", attributes=" + attributes + "]";
	}

}
