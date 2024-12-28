package com.employee_service.config;

import com.employee_service.utility.SecurityUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.employee_service.dto.clientDto.ResourceDto;
import com.employee_service.client.AuthServiceClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final AuthServiceClient authServiceClient;
    private final SecurityUtil securityUtil;

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> roles = extractAuthorities(jwt);
        return new JwtAuthenticationToken (jwt, roles);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
        if (realmAccess != null && !realmAccess.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<String> keycloakRoles = mapper.convertValue(realmAccess.get(ROLES_CLAIM),
                    new TypeReference<List<String>>() {});
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String keycloakRole : keycloakRoles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(keycloakRole));
            }
            return grantedAuthorities;
        }
        return new ArrayList<>();
    }

    public boolean hasPermission(String resourceName) {

        Jwt jwt = securityUtil.getUserJwt();
        Collection<GrantedAuthority> userRoles = extractAuthorities(jwt);

        String tenantIdClaim = securityUtil.getTenantId();
        if (tenantIdClaim == null || tenantIdClaim.isEmpty()) {
            throw new AccessDeniedException(
                    "Access Denied - Tenant information is missing.");
        }

        UUID tenantId = UUID.fromString(tenantIdClaim);
        ResourceDto resource;
        try {
            resource = authServiceClient.getResourceByName(tenantId, resourceName);
        } catch (AccessDeniedException e) {
            throw new AccessDeniedException(
                    "Access Denied - Resource " + resourceName + " not found.");
        }

        Set<String> requiredRoles = resource.getRequiredRoles();
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            throw new AccessDeniedException(
                    "Access Denied - No roles are assigned to access this resource.");
        }

        for (GrantedAuthority authority : userRoles) {
            if (requiredRoles.contains(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
