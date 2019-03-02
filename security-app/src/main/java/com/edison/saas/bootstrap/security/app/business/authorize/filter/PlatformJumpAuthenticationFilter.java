package com.edison.saas.bootstrap.security.app.business.authorize.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Order()
//@Component
public class PlatformJumpAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {

    @Autowired
    private JumpAuthenticationProvider jumpAuthenticationProvider;

    public PlatformJumpAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
    }
    public PlatformJumpAuthenticationFilter() {
        super(new AntPathRequestMatcher("/security/login/jump", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            throw new AuthenticationServiceException(
                    "Authentication token can not be empty" );
        }

        JumpAuthenticationToken jumpAuthenticationToken = new JumpAuthenticationToken(token);

        return jumpAuthenticationProvider.authenticate(jumpAuthenticationToken);

    }


}
