package uss.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uss.dao.TestDao;
import uss.model.TestResult;
import uss.service.TestService;

@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	/*
	 * 일반적으로 Controller에서 Repository를 바로 불러오는 방식보다는
	 * Service 레이어에서 요청하는 방식으로 진행됩니다.
	 * 
	 * Controller는 상황에 따른 흐름정도만 명확하게 보이는 게 좋을 것 같아요.
	 */
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestDao testDao;

	@RequestMapping(method = RequestMethod.GET)
	/*
	 * spring을 사용할 경우엔 HttpServletRequest, HttpServletResponse가 직접적으로 필요하지 않습니다.
	 * 따라서 필요한 파라미터만 받아오는 것일 일반적인 사용방식입니다.
	 * 
	public TestResult find(HttpServletRequest request, HttpServletResponse response) throws Exception
	 */
	public TestResult find(@RequestParam("stringId") String stringId) {
		//String stringId = ServletRequestUtils.getStringParameter(request, "stringId");

		TestResult testResult = testService.find(stringId);
//		TestResult testResult = testDao.find(stringId);

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
