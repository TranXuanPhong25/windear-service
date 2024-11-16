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

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 */
@EnableWebSecurity
@Configuration
@ComponentScan
public class SecurityConfig {
   @Value("${app.security.role.namespace}")
   private String customRoleNamespace;
   
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
      return http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                  .requestMatchers("/api/public", "api/**","api/books/**").permitAll()
                  .requestMatchers("/api/private").authenticated()
//                  .requestMatchers("/api/private-scoped").hasAuthority("SCOPE_read:messages")
                  .requestMatchers("/api/admin").hasAuthority("ROLE_admin")
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