package lib.mapping.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import lib.mapping.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class HttpForTest implements Http {
	private static final Logger logger = LoggerFactory.getLogger(HttpForTest.class);

	Map<String, String> parameters = new HashMap<String, String>();

	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	public String setParameter(String name, String value) {
		return parameters.put(name, value);
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass, String name) {
		Gson gson = DateBuilder.getGsonBuilder(cLass);
		return gson.fromJson(parameters.get(name), cLass);
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass) {
		Gson gson = DateBuilder.getGsonBuilder(cLass);
		return gson.fromJson(gson.toJson(parameters), cLass);
	}

	private String path;

	@Override
	public void forword(String path) throws ServletException, IOException {
		this.path = path;
	}

	private String contentType;

	@Override
	public void setContentType(String type) {
		this.contentType = type;
	}

	private String httpResult = new String();

	@Override
	public void write(String string) {
		httpResult += string;
	}

	private List<String> uriVariables = new ArrayList<String>();

	@Override
	public void addUriVariable(String uriVariable) {
		uriVariables.add(uriVariable);
	}

	@Override
	public String getUriVariable(int number) {
		return uriVariables.get(number);
	}

	private String characterEncoding;

	@Override
	public void setCharacterEncoding(String encording) throws UnsupportedEncodingException {
		characterEncoding = encording;
	}

	@Override
	public void sendNotFound() {
		errorNo = 404;
	}

	private Map<String, Object> sessionAttribute = new HashMap<String, Object>();

	@Override
	public void setSessionAttribute(String name, Object value) {
		sessionAttribute.put(name, value);
	}

	@Override
	public void removeSessionAttribute(String name) {
		sessionAttribute.remove(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) sessionAttribute.get(name);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return sessionAttribute.get(name);
	}

	private String redirect;

	@Override
	public void sendRedirect(String location) {
		redirect = location;
	}

	private Integer errorNo;

	@Override
	public void sendError(int errorNo) {
		this.errorNo = errorNo;
	}

	@Override
	public void sendError(int errorNo, String errorMesage) {
		this.errorNo = errorNo;
	}

	@Override
	public void render() {
		logger.debug(this.toString());
		if (view != null)
			logger.debug(view.toString());
	}

	@Override
	public String toString() {
		String result = "";
		if (!parameters.isEmpty())
			result += "parameters=" + parameters + ", ";
		if (path != null)
			result += "path=" + path + ", ";
		if (contentType != null)
			result += " contentType=" + contentType + ", ";
		if (!httpResult.equals(""))
			result += "httpResult=" + httpResult + ", ";
		if (!uriVariables.isEmpty())
			result += "uriVariables=" + uriVariables + ", ";
		if (characterEncoding != null)
			result += "characterEncoding=" + characterEncoding + ", ";
		if (!sessionAttribute.isEmpty())
			result += "sessionAttribute=" + sessionAttribute + ", ";
		if (redirect != null)
			result += "redirect=" + redirect + ", ";
		if (errorNo != null)
			result += "errorNo=" + errorNo + ", ";
		if (!attribute.isEmpty())
			result += "attribute=" + attribute;
		return result;
	}

	private View view;

	@Override
	public void setView(View view) {
		this.view = view;
	}

	Map<String, Object> attribute = new HashMap<String, Object>();

	@Override
	public Object getAttribute(String key) {
		return attribute.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attribute.put(key, value);
	}

	public View getView() {
		return view;
	}

}
