package com.edison.saas.bootstrap.security.app.business.authorize.service.impl;


import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityAuthority;
import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.bootstrap.security.app.business.authorize.service.LoginService;
import com.edison.saas.bootstrap.security.model.LoginRequest;
import com.edison.saas.bootstrap.security.model.LoginResult;
import com.edison.saas.platform.services.application.business.security.dto.Authenticate;
import com.edison.saas.platform.services.application.business.security.dto.AuthenticateResult;
import com.edison.saas.platform.services.application.business.security.dto.UpdatePasswordRequest;
import com.edison.saas.platform.services.application.business.security.dto.UpdatePasswordResponse;
import com.edison.saas.platform.services.application.client.security.AccountClient;
import com.edison.saas.platform.services.application.client.security.AuthenticateClient;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by gonghongrui on 2017/1/12.
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private AuthenticateClient authenticateClient;

	@Autowired
	private AccountClient accountClient;

	public LoginResult login(LoginRequest loginRequest){

		Exception exception = new RuntimeException();
		exception.printStackTrace();

		LoginResult loginResult = new LoginResult(false, "未进行认证操作");
		Date date1 = new Date();
		LOGGER.info("{}");
		//String password = passwordEncoder.encode(loginRequest.getPassword());

		Date date2 = new Date();
		LOGGER.info(" encode password cost:{}", date2.getTime() - date1.getTime());

		Date date3 = new Date();
		LOGGER.info(" find account cost:{}", date3.getTime() - date2.getTime());

		Authenticate authenticate = new Authenticate();
		authenticate.setAccountName(loginRequest.getUsername());
		authenticate.setPassword(loginRequest.getPassword());
		AuthenticateResult authenticateResult = authenticateClient.authenticate(authenticate).getData();

		if(authenticateResult.isSuccess()){

			AccountVO accountVO = accountClient.get(authenticateResult.getAccountId()).getData();

			SecurityUser securityUser = new SecurityUser(accountVO);

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(securityUser, "N/A", Collections.<GrantedAuthority>emptyList());
			SecurityContextHolder.getContext().setAuthentication(token);

			loginResult = new LoginResult(true, "登录成功");

			if("actuator".equals(loginRequest.getUsername())){
				securityUser.addAuthority(new SecurityAuthority("ACTUATOR"));
			}
		}
		else{
			LOGGER.info("{} login fail with password:{}", loginRequest.getUsername(), loginRequest.getPassword());
			loginResult = new LoginResult(false, authenticateResult.getMessage());
		}
		Date date4 = new Date();
		LOGGER.info(" login cost:{}", date4.getTime() - date3.getTime());
		return loginResult;

	}

	public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request){

		return authenticateClient.updatePassword(request).getData();
	}

	/**
	 * 获取可用资源
	 * @return
	 */
	@PostMapping("/getResource")
	public List<ResourceVO> getResource(Long appCode, Long accountId){
		return authenticateClient.getResource(appCode, accountId).getData();
	}
}
