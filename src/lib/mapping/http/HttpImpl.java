package lib.mapping.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.mapping.view.View;

import com.google.gson.Gson;

public class HttpImpl implements Http {

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private ArrayList<String> uriVariables;
	private View view;

	@Override
	public String getParameter(String name) {
		return req.getParameter(name);
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass, String name) {
		Gson gson = DateBuilder.getGsonBuilder(cLass);
		return gson.fromJson(req.getParameter(name), cLass);
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass) {
		Gson gson = DateBuilder.getGsonBuilder(cLass);
		return gson.fromJson(gson.toJson(req.getParameterMap()), cLass);
	}

	public HttpImpl(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	@Override
	public void forword(String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	@Override
	public void setContentType(String type) {
		resp.setContentType(type);
	}

	@Override
	public void write(String string) {
		try {
			resp.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addUriVariable(String group) {
		if (uriVariables == null)
			uriVariables = new ArrayList<String>();
		uriVariables.add(group);
	}

	@Override
	public String getUriVariable(int number) {
		return uriVariables.get(number);
	}

	@Override
	public void setCharacterEncoding(String encording) throws UnsupportedEncodingException {
		req.setCharacterEncoding(encording);
		resp.setCharacterEncoding(encording);
	}

	@Override
	public void sendNotFound() {
		try {
			resp.sendError(404, req.getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	@Override
	public void removeSessionAttribute(String name) {
		req.getSession().removeAttribute(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) req.getSession().getAttribute(name);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	@Override
	public void sendRedirect(String location) {
		try {
			resp.sendRedirect(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(int errorNo) {
		try {
			resp.sendError(errorNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(int errorNo, String errorMesage) {
		try {
			resp.sendError(errorNo, errorMesage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		if (view == null)
			return;
		view.render(this);
	}

	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void setAttribute(String key, Object value) {
		req.setAttribute(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return req.getAttribute(key);
	}

}
