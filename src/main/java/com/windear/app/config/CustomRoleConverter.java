package com.windear.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class CustomRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
   public String roleNamespace = "https://windear.vercel.app/roles";
   
   @Override
   public Collection<GrantedAuthority> convert(Jwt jwt) {
      List<String> roles = jwt.getClaimAsStringList(roleNamespace); // Extracting roles from the "roles" claim
      if (roles == null) {
         return Collections.emptyList();
      }
      return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Prefix with "ROLE_"
            .collect(Collectors.toList());
   }
}
