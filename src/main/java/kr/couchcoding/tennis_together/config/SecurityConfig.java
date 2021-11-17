package kr.couchcoding.tennis_together.config;

import kr.couchcoding.tennis_together.config.auth.AuthFilterContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.couchcoding.tennis_together.domain.user.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    
    @Autowired
    private AuthFilterContainer authFilterContainer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF 보호기능 disable
                .authorizeRequests() // 요청에대한 권한 지정
                .anyRequest().authenticated() // 모든 요청이 인증되어야한다.
                .and()
                .addFilterBefore(authFilterContainer.getFilter(),// 커스텀 필터인 JwtFilter를 먼저 수행한다.
                        UsernamePasswordAuthenticationFilter.class)        // 이후 UsernamePasswordAuthenticationFilter 실행
                .exceptionHandling() // 예외처리 기능 작동
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); // 인증실패시처리
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 인증 예외 URL설정
        web.ignoring().antMatchers(HttpMethod.POST, "/users")
                .antMatchers("/")
                .antMatchers("/locations")
                .antMatchers("/locations/**")
                .antMatchers("/courts")
                .antMatchers("/courts/**")
                .antMatchers(HttpMethod.GET,"/games")
                .antMatchers(HttpMethod.GET,"/games/**")
                .antMatchers("/assets/**")
                .antMatchers("/images/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/static/**")
                .antMatchers("/error")
                .antMatchers("/error/**")
                //.antMatchers(HttpMethod.GET, "/users/**")

        ;
    }

}
