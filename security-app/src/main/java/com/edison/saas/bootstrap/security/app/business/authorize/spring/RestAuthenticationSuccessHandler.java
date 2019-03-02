package com.edison.saas.bootstrap.security.app.business.authorize.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.bootstrap.security.model.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的处理
 * 1.捞到用户的所有权限
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        clearAuthenticationAttributes(request);

        SecurityUser userDetails = (SecurityUser)authentication.getPrincipal();

        Long accountId = userDetails.getAccount().getId();

        LoginResult loginResult = new LoginResult(true);
        loginResult.setSuccess(true);
        loginResult.setSessionId(request.getSession().getId());


        String originsList = encodeHeader(request.getHeader(ORIGIN));

        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, originsList);
        response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With,Content-Type,Accept,Origin,x-requested-with,Authorization");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "*");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "1800");

        response.getWriter().write(objectMapper.writeValueAsString(loginResult));
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