package uss.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import uss.dao.UserDao;
import uss.model.User;
import uss.service.UserService;

import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public User find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String stringId = ServletRequestUtils.getStringParameter(request, "stringId");
		User user = userDao.find(stringId);
		logger.debug("get test result userId : {} result: {}", stringId, user.toString());
		return user;
	}

	@RequestMapping(method = RequestMethod.POST)
	/*
	 * 보통 이런 경우엔 JSON데이터를 직접 생성하지 않고 @ModelAttribute라는 어노테이션을 사용합니다.
	 * test를 진행하지 않아서 안 돌아갈 수 있으니 참고 바랍니다.
	
	public void insert(@ModelAttribute User user) throws Exception {
		userService.insert(user);
		logger.debug("insert test result userId : {}", user.getStringId());
	}
	 */
	public void insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userString = ServletRequestUtils.getStringParameter(request, "user");
		Gson gson = new Gson();
		User user = gson.fromJson(userString, User.class);
		userDao.insert(user);
		logger.debug("insert test result userId : {}", user.getStringId());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userString = ServletRequestUtils.getStringParameter(request, "user");
		Gson gson = new Gson();
		User user = gson.fromJson(userString, User.class);
		User fromDB = userDao.find(user.getStringId());

		Map<String, String> error = new HashMap<String, String>();

		if (fromDB == null) {
			error.put("error", "not exist id");
			return gson.toJson(error);
		}
		if (!fromDB.getPassword().equals(user.getPassword())) {
			error.put("error", "wrong password");
			return gson.toJson(error);
		}
		error.put("error", "Success!!");
		return gson.toJson(error);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String userString = ServletRequestUtils.getStringParameter(request, "user");
		Gson gson = new Gson();
		User user = gson.fromJson(userString, User.class);
		userDao.update(user);
		logger.debug("update test result userId : {}", user.getStringId());
		return mav;
	}

}
