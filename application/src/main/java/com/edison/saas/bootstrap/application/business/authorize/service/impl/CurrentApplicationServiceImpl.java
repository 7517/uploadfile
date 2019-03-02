package com.edison.saas.bootstrap.application.business.authorize.service.impl;


import com.edison.saas.bootstrap.application.business.authorize.service.CurrentApplicationService;
import com.edison.saas.platform.services.application.client.application.AppClient;
import com.edison.saas.platform.services.platform.business.application.vo.AppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gonghongrui on 2019/1/12.
 */
@Service
public class CurrentApplicationServiceImpl implements CurrentApplicationService {

	@Autowired
	private AppClient appClient;

	@Override
	public AppVO getApp(Long appCode) {
		return appClient.getByAppCode(appCode).getData();
	}




}
