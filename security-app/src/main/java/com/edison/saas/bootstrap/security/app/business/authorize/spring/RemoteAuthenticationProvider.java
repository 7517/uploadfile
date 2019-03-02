package com.edison.saas.bootstrap.security.app.business.authorize.spring;

import com.edison.saas.bootstrap.security.app.business.authorize.service.LoginService;
import com.edison.saas.bootstrap.security.model.LoginRequest;
import com.edison.saas.bootstrap.security.model.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Slf4j
public class RemoteAuthenticationProvider  extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginService loginService;



    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername(authentication.getPrincipal().toString());
        loginRequest.setPassword(authentication.getCredentials().toString());

        LoginResult loginResult = loginService.login(loginRequest);
        if(!loginResult.isSuccess()){
            throw new BadCredentialsException(messages.getMessage(
                    "RemoteAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;

        if(StringUtils.isEmpty(username)){
            throw new InternalAuthenticationServiceException("username is empty");
        }
        try {

            loadedUser = userDetailsService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException notFound) {

            throw notFound;
        }
        catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
}
