package com.windear.app.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@ComponentScan
public class SecurityConfig {
    @Value("${app.security.role.namespace}")
    private String customRoleNamespace;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/api/public",
                                "/api/search",
                                "/api/external/**",
                                "/api/news/**",
                                "/api/books/**",
                                "/api/review/book/**",
                                "/api/bookloan/**",
                                "/api/shelves/**",
                                "/api/shelves",
                                "/api/db/books",
                                "/api/db/books/**",
                                "/api/genres",
                                "/api/review",
                                "/api/review/**",
                                "/api/popular-book/top10"

                        )
                        .permitAll()
                        .requestMatchers(
                                "/api/private",
                                "/api/auth0/user/**",
                                "/api/review",
                                "/api/review/**"
                        )
                        .authenticated()
                        .requestMatchers(
                                "/api/admin",
                                "/api/auth0/users",
                                "/api/auth0/stats/**",
                                "/api/auth0/logs"
                        )
                        .hasAuthority("ROLE_admin")

                )
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(customize -> {
                            JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
                            jwtConverter.setJwtGrantedAuthoritiesConverter(new CustomRoleConverter(customRoleNamespace));
                            customize.jwtAuthenticationConverter(jwtConverter);
                        })
                )
                .build();
    }
}