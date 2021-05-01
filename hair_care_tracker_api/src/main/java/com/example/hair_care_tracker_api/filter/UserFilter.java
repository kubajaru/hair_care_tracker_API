package com.example.hair_care_tracker_api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter class. Responsible for handling JWT token parsing and authorization.
 */
public class UserFilter implements javax.servlet.Filter {
    /**
     * Method that comes from the implemented interface. It checks if the token was provided with request.
     * Parses the retrieved JWT token and adds email of the user to the servlet request for further handling.
     *
     * @param servletRequest  what comes
     * @param servletResponse what goes
     * @param filterChain     places filter in the chain if we had more then one
     * @throws IOException      comes with the interface
     * @throws ServletException when the header is empty or invalid or when the parsing JWT fails
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // claims = JWT token representation.
                Claims claims = Jwts.parser().setSigningKey("oCIZHIEqSmBcO1O").parseClaimsJws(token).getBody();
                servletRequest.setAttribute("email", claims.getSubject());
            } catch (Exception ex) {
                throw new ServletException("Wrong key");
            }
        } else {
            throw new ServletException("Wrong or empty header");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
