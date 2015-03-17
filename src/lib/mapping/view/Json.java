package lib.mapping.view;

import lib.mapping.dispatch.support.Http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Json implements View {

	private Object jsonObj;
	private String dateformat;

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public Json() {
	}

	public void setJsonObj(Object jsonObj) {
		this.jsonObj = jsonObj;
	}

	public Json(Object obj) {
		this.jsonObj = obj;
	}

	@Override
	public String toString() {
		return "Json [jsonObj=" + jsonObj + ", dateformat=" + dateformat + "]";
	}

	@Override
	public void render(Http http) {
		Gson gson;
		if(dateformat!=null)
			gson = new GsonBuilder().setDateFormat(dateformat).create();
		else
			gson = new Gson();
		http.setContentType("application/json");
		http.write(gson.toJson(jsonObj));
	}

	public Object getJsonObj() {
		return jsonObj;
	}

}
