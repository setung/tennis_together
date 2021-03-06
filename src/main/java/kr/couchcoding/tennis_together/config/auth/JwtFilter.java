package kr.couchcoding.tennis_together.config.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import kr.couchcoding.tennis_together.exception.CustomException;
import kr.couchcoding.tennis_together.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter{

    private UserDetailsService userDetailsService;
    private FirebaseAuth firebaseAuth;

    public JwtFilter(UserDetailsService userDetailsService, FirebaseAuth firebaseAuth){

        this.userDetailsService = userDetailsService;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        FirebaseToken decodedToken;

        // 토큰을 받아와 검증
        try{

            String header = RequestUtil.getAuthorizationToken((request.getHeader("Authorization")));
            decodedToken = firebaseAuth.verifyIdToken(header);

        } catch (FirebaseAuthException | IllegalArgumentException e){
            // ErrorMessage 응답 전송
            log.info("token verify exception: " + e.getMessage());
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
            return ;
        }

        // User를 가져와 SecurityContext에 저장
        try{
            UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch(NoSuchElementException | CustomException e){
            log.info("user found exception : " + e.getMessage());
            // ErrorMessage 응답 전송
            response.setStatus(HttpStatus.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
            return;
        }
        // 요청, 응답시 filter호출
        filterChain.doFilter(request, response);
    }
}
