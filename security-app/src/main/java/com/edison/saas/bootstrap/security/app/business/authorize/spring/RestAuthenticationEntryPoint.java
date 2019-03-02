package com.edison.saas.bootstrap.security.app.business.authorize.spring;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问应用资源时, 权限不通过的处理
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        if (isAjaxRequest(request)) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + authException.getMessage());
//        } else {
//            response.sendRedirect("/login.html");
//        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + authException.getMessage());

    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equalsIgnoreCase(ajaxFlag);
    }
}