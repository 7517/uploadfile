package com.edison.saas.bootstrap.security.app.business.authorize;


import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.common.framework.app.ApplicationProperties;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 获取登录用户的工具类
 */
@Service
public class SecurityUtilImpl implements SecurityUtil {

    @Value("${application.code:-1}")
    private String appCode;

    @Autowired
    private ApplicationProperties applicationProperties;

    public AccountVO getAccount(){
        SecurityExpressionRoot root = null;

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if(authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof SecurityUser)){
            //throw new BusinessException("security", "account", "not login", "用户未登陆");
           return null;
        }

        SecurityUser userDetails = (SecurityUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        AccountVO account = userDetails.getAccount();

        return account;

    }

    public String getAppCode() {
        return applicationProperties.getCode()+"";
    }

    @Override
    public Long getTenantId() {
        AccountVO accountVO = this.getAccount();
        if(accountVO != null)
            return accountVO.getTid();
        return null;
    }


    @Override
    public Long getAccountId() {
        AccountVO accountVO = this.getAccount();
        if(accountVO != null)
            return accountVO.getId();
        return null;
    }
}
