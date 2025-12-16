package com.github.sveldevorls.readtogether.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final JwtUserPrincipal principal;

    public JwtAuthenticationToken(int id, String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = new JwtUserPrincipal(id, username);
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
