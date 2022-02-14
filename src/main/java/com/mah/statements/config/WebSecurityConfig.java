package com.mah.statements.config;

import com.mah.statements.util.enums.UserRole;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles(UserRole.ROLE_ADMIN.getRole())
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles(UserRole.ROLE_USER.getRole());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic()
                .and()
                    .authorizeRequests()
                .antMatchers("/actuator/*").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .sessionManagement()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login")
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<ViewStatementsFilterConfig> viewStatementsFilterConfigFilter() {

        FilterRegistrationBean<ViewStatementsFilterConfig> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ViewStatementsFilterConfig());
        registrationBean.addUrlPatterns("/api/statements/viewStatements/*");

        return registrationBean;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
