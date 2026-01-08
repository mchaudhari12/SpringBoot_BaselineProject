package org.studyeasy.SpringStarter.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.studyeasy.SpringStarter.util.Constant.Privillages;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final String[] WHITELIST = {
        "/",
        "/register",
        "/css/**",
        "/fonts/**",
        "/images/**",
        "/js/**",
        "/upload/**",
        "/post/**",
        "/forgot-password"
        
    };

    // Password Encoder Bean Defination
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers(WHITELIST)
                .permitAll()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/update_photo/**").authenticated()
                .requestMatchers("/posts/add/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/editor/**").hasAnyRole("ADMIN", "EDITOR")
                .requestMatchers("/test/**").hasAuthority(Privillages.ACCESS_ADMIN_PANEL.getPrivillage())
                .and()
        .formLogin()
        .loginPage("/login").loginProcessingUrl("/login")
        .usernameParameter("email").passwordParameter("password")
        .defaultSuccessUrl("/", true).failureUrl("/login?error")
        .permitAll()
        .and()
        .logout().logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .and()
        .rememberMe().rememberMeParameter("remember-me");


        return http.build();
    }
}
