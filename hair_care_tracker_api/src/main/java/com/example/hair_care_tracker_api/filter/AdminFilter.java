package com.example.hair_care_tracker_api.filter;

import com.example.hair_care_tracker_api.exception.NotAdminException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            Claims claims;
            try {
                // claims = JWT token representation.
                claims = Jwts.parser().setSigningKey("oCIZHIEqSmBcO1O").parseClaimsJws(token).getBody();
            } catch (Exception ex) {
                throw new ServletException("Wrong admin key", ex);
            }
            if (claims.get("role").equals("admin")) {
                servletRequest.setAttribute("email", claims.getSubject());
            } else {
                throw new NotAdminException();
            }
        } else {
            throw new ServletException("Wrong or empty header");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
