package org.leafall.tokenservicestarter.component;

import org.leafall.tokenservicestarter.service.AuthContextHolder;
import org.leafall.tokenservicestarter.service.AuthContextResolver;
import jakarta.servlet.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TokenFilter implements Filter {

    private final AuthContextResolver authContextResolver;
    private final String AUTHORIZATION = "Authorization";

    public TokenFilter(AuthContextResolver authContextResolver) {
        this.authContextResolver = authContextResolver;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var req = (HttpServletRequest) servletRequest;

        try {
            var authContext = authContextResolver.getContextFromToken(req.getHeader(AUTHORIZATION));
            if (authContext != null) {
                AuthContextHolder.set(authContext);
            }

            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } finally {
                AuthContextHolder.remove();
            }
        } catch (Exception ex) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
