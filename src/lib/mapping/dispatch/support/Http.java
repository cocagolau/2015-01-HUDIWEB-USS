package lib.mapping.dispatch.support;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.mapping.view.View;

public class Http {

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private ArrayList<String> params;
	private View view;

	public HttpServletRequest getReq() {
		return req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public String getParameter(String name) {
		return req.getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return req.getParameterMap();
	}

	public Http(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void forword(String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	public void setContentType(String type) {
		resp.setContentType(type);
	}

	public void write(String string) {
		try {
			resp.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addParameter(String group) {
		if(params==null)
			params = new ArrayList<String>();
		params.add(group);
	}


	public String getUriVariable(int number) {
		return params.get(number);
	}

	public void setCharacterEncoding(String encording) throws UnsupportedEncodingException {
		req.setCharacterEncoding(encording);
		resp.setCharacterEncoding(encording);
	}

	public void sendNotFound() {
		try {
			resp.sendError(404, req.getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSessionAttribute(String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	public void removeSessionAttribute(String name) {
		req.getSession().removeAttribute(name);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) req.getSession().getAttribute(name);
	}

	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	public void sendRedirect(String location) {
		try {
			resp.sendRedirect(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendError(int errorNo) {
		try {
			resp.sendError(errorNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendError(int errorNo, String errorMesage) {
		try {
			resp.sendError(errorNo, errorMesage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render() {
		if(view==null)
			return;
		view.render(this);
	}

	public void setView(View view) {
		this.view = view;
	}


}
