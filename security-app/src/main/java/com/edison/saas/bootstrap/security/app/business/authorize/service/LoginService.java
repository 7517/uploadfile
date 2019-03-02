package com.edison.saas.bootstrap.security.app.business.authorize.service;


import com.edison.saas.bootstrap.security.model.LoginRequest;
import com.edison.saas.bootstrap.security.model.LoginResult;
import com.edison.saas.platform.services.application.business.security.dto.UpdatePasswordRequest;
import com.edison.saas.platform.services.application.business.security.dto.UpdatePasswordResponse;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;

import java.util.List;

/**
 * Created by gonghongrui on 2017/1/12.
 */
public interface LoginService {

	LoginResult login(LoginRequest loginRequest);
	UpdatePasswordResponse updatePassword(UpdatePasswordRequest request);

	/**
	 * 获取可用资源
	 * @return
	 */
	List<ResourceVO> getResource(Long appCode, Long accountId);
}
