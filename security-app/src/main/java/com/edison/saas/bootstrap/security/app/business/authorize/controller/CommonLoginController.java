package com.edison.saas.bootstrap.security.app.business.authorize.controller;


import com.edison.saas.bootstrap.security.app.business.authorize.service.LoginService;
import com.edison.saas.bootstrap.security.model.LoginRequest;
import com.edison.saas.bootstrap.security.model.LoginResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 管理账号
 */
@Controller
public class CommonLoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonLoginController.class);

	@Value("${application.code:-1}")
	private Long appCode;

	@Autowired
	private LoginService loginService;


	/**
	 * 登陆
	 * @param username 登录请求
	 * @return
	 */
	@PostMapping(value = "/security/login/authenticate1")
	@ResponseBody
	@ApiOperation(value = "一般登录", notes = "一般登录", httpMethod = "POST", consumes = "application/xml")
	public LoginResult authenticate(@RequestParam String username, @RequestParam String password, HttpSession session){

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword(password);
		loginRequest.setUsername(username);

		LoginResult loginResult = loginService.login(loginRequest);

		loginResult.setSessionId(session.getId());

		LOGGER.debug("{}", loginResult);

		return loginResult;

	}



}
