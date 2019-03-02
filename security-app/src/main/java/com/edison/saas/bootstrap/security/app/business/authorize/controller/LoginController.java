package com.edison.saas.bootstrap.security.app.business.authorize.controller;


import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.bootstrap.security.app.business.authorize.service.JumpLoginRibbonService;
import com.edison.saas.bootstrap.security.app.business.authorize.service.LoginService;
import com.edison.saas.bootstrap.security.model.LoginRequest;
import com.edison.saas.bootstrap.security.model.LoginResult;
import com.edison.saas.platform.services.application.business.security.dto.UpdatePasswordRequest;
import com.edison.saas.platform.services.application.business.security.dto.UpdatePasswordResponse;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceTreeNode;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 管理账号
 */
@Api(description = "登录和安全相关", tags = "Login")
@RestController
@RequestMapping(value = "/security/login")
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Value("${application.code:-1}")
	private Long appCode;

	@Autowired
	private LoginService loginService;


	@Autowired
	private JumpLoginRibbonService jumpLoginRibbonService;


	/**
	 * 登陆
	 * @param loginRequest 登录请求
	 * @return
	 */
	@PostMapping(value = "/authenticate")
	@ApiOperation(value = "一般登录", notes = "一般登录", httpMethod = "POST", consumes = "application/json")
	public LoginResult login(@RequestBody @Valid LoginRequest loginRequest, @ApiIgnore HttpSession session){

		LoginResult loginResult = loginService.login(loginRequest);

		loginResult.setSessionId(session.getId());

		LOGGER.debug("{}", loginResult);

		return loginResult;

	}

	/**
	 * 跳转进入
	 * @param token 跳转令牌
	 * @return
	 */
	@PostMapping(value = "/jump")
	@ApiOperation(value = "从别的系统跳转进入", notes = "从别的系统跳转进入", httpMethod = "POST")
	public LoginResult jump(@ApiParam("使用的令牌") @RequestParam("token") String token, @ApiIgnore HttpSession session){

		LoginResult loginResult = jumpLoginRibbonService.authenticate(appCode+"", token);

		loginResult.setSessionId(session.getId());

		LOGGER.debug("{}", loginResult);

		return loginResult;

	}


	/**
	 * 修改当前用户密码
	 * @return
	 */
	@PostMapping("/updatePassword")
	public UpdatePasswordResponse updatePassword(@RequestBody UpdatePasswordRequest request){

		SecurityUser userDetails = (SecurityUser) SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();

		AccountVO account = userDetails.getAccount();
		//把账号设置成当前登录用户的账号
		request.setAccountId(account.getId());
		UpdatePasswordResponse response = loginService.updatePassword(request);

		return response;

	}

	/**
	 * 获取可用资源
	 * @return
	 */
	@PostMapping("/getResource")
	public List<ResourceTreeNode> getResource(@AuthenticationPrincipal SecurityUser securityUser){

		AccountVO account = securityUser.getAccount();

		//整理成树形结构
		List<ResourceVO>  resourceVOList = loginService.getResource(appCode, account.getId());

		return ResourceUtil.convert(resourceVOList);

	}

}
