package com.edison.saas.bootstrap.security.app.business.authorize;


import com.edison.saas.common.framework.tenant.SaaSUtil;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;

/**
 * 获取登录用户的工具类
 */
public interface SecurityUtil extends SaaSUtil {

    AccountVO getAccount();
    String getAppCode();
}
