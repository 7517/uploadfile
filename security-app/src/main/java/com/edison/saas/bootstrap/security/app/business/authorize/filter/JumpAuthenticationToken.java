package com.edison.saas.bootstrap.security.app.business.authorize.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter @Setter
public class JumpAuthenticationToken extends AbstractAuthenticationToken {

    private String credentials;

    private Object principal;


    public JumpAuthenticationToken(String token) {
        super(null);
        this.credentials = token;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
