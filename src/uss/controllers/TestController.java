package uss.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import com.google.gson.annotations.JsonAdapter;

import uss.dao.TestDao;
import uss.model.TestResult;

@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestDao testDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public TestResult find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String stringId = ServletRequestUtils.getStringParameter(request, "stringId");
		
		TestResult testResult = testDao.find(stringId);
		
		logger.debug("get test result userId : {} result: {}", stringId, testResult.toString());
		return testResult;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String stringId = ServletRequestUtils.getStringParameter(request, "stringId");
		String result = ServletRequestUtils.getStringParameter(request, "result");
		TestResult testResult = new TestResult(stringId, result);
		testDao.insert(testResult);
		
		logger.debug("insert test result userId : {}", stringId);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String stringId = ServletRequestUtils.getStringParameter(request, "stringId");
		String result = ServletRequestUtils.getStringParameter(request, "result");
		TestResult testResult = new TestResult(stringId, result);
		testDao.update(testResult);
		
		logger.debug("update test result userId : {}", stringId);
		return mav;
	}
	
}
