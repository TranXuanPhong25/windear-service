package com.windear.app.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers(HttpMethod.GET,
                                "/api/search",
                                "/api/external/**",
                                "/api/books/**",
                                "/api/review/book/**",
                                "/api/db/books",
                                "/api/db/books/**",
                                "/api/genres",
                                "/api/review",
                                "/api/popular-book/top10",
                                "/api/review",
                                "/api/review/**",
                                "/api/bookloan/book/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/external/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/auth0/user/**",
                                "/api/notification",
                                "/api/notification/**",
                                "/api/review/**",
                                "/api/shelves/**",
                                "/api/shelves",
                                "/api/bookloan",
                                "/api/bookloan/**"
                        )
                        .authenticated()
                        .requestMatchers(
                                "/api/auth0/users",
                                "/api/auth0/stats/**",
                                "/api/auth0/logs",
                                "/api/analytic/**",
                                "/api/db/books",
                                "/api/db/books/**",
                                "/api/popular-book/**"

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
