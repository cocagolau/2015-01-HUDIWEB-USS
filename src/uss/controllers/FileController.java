package uss.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import uss.dao.UserDao;

@RestController
public class FileController {

	@Autowired
	private UserDao userDao;

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(new MyPlainHtmlView("register.html"));
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(new MyPlainHtmlView("login.html"));
	}
	@ResponseBody
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(new MyPlainHtmlView("profile.html"));
	}

	public class MyPlainHtmlView implements View {

		private final String htmlDocument;

		public MyPlainHtmlView(String htmlDocument) {
			this.htmlDocument = htmlDocument;
		}

		@Override
		public String getContentType() {
			return null;
		}

		@Override
		public void render(Map<String, ?> model, javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws Exception {
			ServletOutputStream out = response.getOutputStream();
			String result = "";
			try (BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource("/" + htmlDocument).getFile()))) {
				String line;
				while ((line = br.readLine()) != null) {
					result += line;
				}
			}
			out.write(result.getBytes("utf-8"));
		}
	}

}
