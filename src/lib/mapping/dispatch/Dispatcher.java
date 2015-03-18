package lib.mapping.dispatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.mapping.dispatch.support.Http;


public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Mapper.execute(req.getRequestURI(), new Http(req, resp));
	}

}
