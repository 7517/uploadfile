package com.edison.saas.bootstrap.security.app.business.authorize.controller;


import com.edison.saas.bootstrap.security.app.business.authorize.SecurityUtil;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 得到当前应用
 */
@RestController
@RequestMapping(value = "/current")
@Slf4j
public class CurrentUserController {

    @Autowired
    private SecurityUtil securityUtil;


    /**
     * 获取当前登录用户
     * @return
     */
    @PostMapping("/account")
    public AccountVO getAccount(){

        AccountVO account =  securityUtil.getAccount();

        return account;
    }

}
