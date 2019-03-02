package com.edison.saas.bootstrap.security.app.config;


import com.edison.saas.bootstrap.security.app.business.authorize.SecurityUtil;
import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

//@WebFilter(urlPatterns = "/**")
//@Component
@Slf4j
public class TestSecurityFilter implements Filter {

    @Value("${management.security.enabled:true}")
    private boolean securityEnabled;

    @Autowired
    private SecurityUtil securityUtil;

    @Override  
    public void init(FilterConfig filterConfig) throws ServletException {

    }

  
    @Override  
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(!securityEnabled && securityUtil.getAccount() == null){
            this.initTestAccount();
        }

//        this.initTestAccount();
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        log.info("URL: {}", request.getRequestURL());

        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String originsList = encodeHeader(request.getHeader(ORIGIN));
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, originsList);
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With, Content-Type, Accept, Origin, x-requested-with, Authorization");
//                response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "*");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "1800");

        filterChain.doFilter(servletRequest, servletResponse);  
    }
  
    @Override  
    public void destroy() {  
  
    }

    private void initTestAccount(){

        AccountVO account = new AccountVO();
        account.setName("超级管理员");
        account.setNickName("超级管理员");
        account.setId(1L);
        account.setTid(1L);

        SecurityUser userDetails = new SecurityUser(account, null);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, Collections.<GrantedAuthority>emptyList());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    static String encodeHeader(final String header) {
        if (header == null) {
            return null;
        }
        // Protect against HTTP response splitting vulnerability
        // since value is written as part of the response header
        // Ensure this header only has one header by removing
        // CRs and LFs
        return header.split("\n|\r")[0].trim();
    }

    // HTTP CORS Response Headers
    static final String ACCESS_CONTROL_ALLOW_ORIGIN =
            "Access-Control-Allow-Origin";
    static final String ACCESS_CONTROL_ALLOW_CREDENTIALS =
            "Access-Control-Allow-Credentials";
    static final String ACCESS_CONTROL_ALLOW_METHODS =
            "Access-Control-Allow-Methods";
    static final String ACCESS_CONTROL_ALLOW_HEADERS =
            "Access-Control-Allow-Headers";
    static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";


    // HTTP CORS Request Headers
    static final String ORIGIN = "Origin";
    static final String ACCESS_CONTROL_REQUEST_METHOD =
            "Access-Control-Request-Method";
    static final String ACCESS_CONTROL_REQUEST_HEADERS =
            "Access-Control-Request-Headers";
}  