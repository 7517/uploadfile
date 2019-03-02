package com.edison.saas.bootstrap.security.app.business.authorize.filter;

import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.platform.services.application.business.security.dto.AppJumpRequest;
import com.edison.saas.platform.services.application.client.security.AccountClient;
import com.edison.saas.platform.services.application.client.security.JumpClient;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

import java.util.Collections;

@Slf4j
public class JumpAuthenticationProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private JumpClient jumpClient;

    @Autowired
    private AccountClient accountClient;

    @Value("${application.code}")
    private String appCode;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(JumpAuthenticationToken.class, authentication,
                messages.getMessage(
                        "JumpAuthenticationProvider.onlySupports",
                        "Only JumpAuthenticationToken is supported"));

        // Determine username
        String token = authentication.getCredentials().toString();

        RestResponse<AppJumpRequest>  restResponse = jumpClient.authenticate(appCode, token);
        if(!restResponse.isSuccess()){

            log.debug("Token '" + token  + "' authenticate fail, message:{}", restResponse.getMessage());

            throw new BadCredentialsException(messages.getMessage(
                    "JumpAuthenticationProvider.badCredentials",
                    restResponse.getMessage()));
        }
        AppJumpRequest appJumpRequest  = restResponse.getData();
        AccountVO accountVO = accountClient.get(appJumpRequest.getAccountId()).getData();

        SecurityUser securityUser = new SecurityUser(accountVO);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(securityUser, "N/A", Collections.<GrantedAuthority>emptyList());
//        SecurityContextHolder.getContext().setAuthentication(token);


//        JumpAuthenticationToken jumpAuthenticationToken = new JumpAuthenticationToken();
//        jumpAuthenticationToken.setAuthenticated(true);
//
//        authentication.setAuthenticated(true);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JumpAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
