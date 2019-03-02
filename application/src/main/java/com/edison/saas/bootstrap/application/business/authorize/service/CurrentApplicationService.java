package com.edison.saas.bootstrap.application.business.authorize.service;


import com.edison.saas.platform.services.platform.business.application.vo.AppVO;

/**
 * Created by gonghongrui on 2017/1/12.
 */
public interface CurrentApplicationService {


	AppVO getApp(Long appCode);

}
