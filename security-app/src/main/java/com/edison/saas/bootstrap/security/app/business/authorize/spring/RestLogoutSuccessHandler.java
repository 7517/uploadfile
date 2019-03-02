package com.edison.saas.bootstrap.security.app.business.authorize.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功后的处理
 */
@Component
@Slf4j
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //super.onLogoutSuccess(request, response, authentication);
        log.info("lo");
        if (isAjaxRequest(request)) {
            response.sendError(HttpServletResponse.SC_OK, "已登出" );
        } else {
            response.sendRedirect("/login.html");
        }
    }


    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}