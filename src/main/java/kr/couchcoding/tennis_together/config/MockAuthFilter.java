package kr.couchcoding.tennis_together.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class MockAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    public MockAuthFilter(UserDetailsService service) {
        super();
        userDetailsService = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String username = token.substring(7);
            if(username.startsWith("test")){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                doFilter(request, response, filterChain);
            } else if (username.equals("unknown")) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "USER_NOT_FOUND");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NO_TOKEN");
        }
    }
  
}
