package com.tiktok.common.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭跨站请求防护
                .csrf()
                .disable()
                //设置会话管理策略为无状态:有些应用可能不需要或不希望使用传统的会话机制。通过将会话管理策略设置为无状态（STATELESS），应用程序不再创建或使用会话，
                // 而是每个请求都被视为独立的，不依赖于之前的会话状态。这种方式通常用于构建无状态的 API 服务，或者采用基于 Token 的身份验证机制。
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 不通过session获取sessionContext;
                .and()
                .authorizeRequests()
                //允许指定路径的请求不进行身份验证
//                .antMatchers("/**")
                .antMatchers("/swagger-ui.html","/swagger-resources/**","/webjars/**","/v2/**","/api/**")
                .permitAll()
                //允许指定路径的请求不进行身份验证
                .anyRequest().authenticated();

    }
}
