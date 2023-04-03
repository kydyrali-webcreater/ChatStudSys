package org.example.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

@Component
public class CustomCorsFilter extends OncePerRequestFilter {

    private String allowedOrigin="http://localhost:8080";

    private Boolean corsEnabled=true;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", getAllowedOrigin());
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST, DELETE, PUT, PATCH, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setIntHeader("Access-Control-Max-Age", 10);
        filterChain.doFilter(request, response);
    }

    private String getAllowedOrigin() {
        return corsEnabled ? allowedOrigin : "*";
    }
}
