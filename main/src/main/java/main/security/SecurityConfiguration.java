package main.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/account/get_balance/**").hasAnyRole("ACCOUNT_HOLDER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/transfer").hasAnyRole("ACCOUNT_HOLDER","ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/transfer").hasAnyRole("ACCOUNT_HOLDER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/transfer/third-party**").hasAnyRole("THIRD_PARTY","ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/transfer/third-party**").hasAnyRole("THIRD_PARTY","ADMIN")
                .requestMatchers(HttpMethod.POST, "/user/third-party/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                .anyRequest().denyAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
