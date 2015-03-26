package lib.mapping.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import lib.mapping.dispatch.support.Http;
import lib.setting.Setting;

public class Jsp implements View {

	private String jspFileName;

	private List<String> keys = new ArrayList<String>();
	private List<Object> objs = new ArrayList<Object>();

	public Jsp() {
	}

	public void setJspFileName(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public Jsp(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public void put(String key, Object obj) {
		keys.add(key);
		objs.add(obj);
	}

	@Override
	public void render(Http http) {
		for (int i = 0; i < keys.size(); i++) {
			http.getReq().setAttribute(keys.get(i), objs.get(i));
		}
		try {
			http.forword(Setting.get("default", "jspPath") + jspFileName);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public String getJspFileName() {
		return jspFileName;
	}

	public List<String> getKeys() {
		return keys;
	}

	public List<Object> getObjs() {
		return objs;
	}
}
