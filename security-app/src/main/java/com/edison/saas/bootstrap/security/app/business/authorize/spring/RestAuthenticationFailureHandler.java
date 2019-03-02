package com.edison.saas.bootstrap.security.app.business.authorize.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        String username = request.getParameter("username");

        log.info("user({}) authentication fail", username);

        logger.debug("No failure URL set, sending 401 Unauthorized error");

        String message = exception.getMessage();

        if (exception instanceof BadCredentialsException) {
            message = "密码错误";
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed: " + message);

        super.onAuthenticationFailure(request, response, exception);

    }
}
