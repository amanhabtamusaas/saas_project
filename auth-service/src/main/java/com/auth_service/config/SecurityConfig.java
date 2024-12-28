package com.auth_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RoleConverter roleConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers( "/swagger-ui/**", "/v3/api-docs/**", "/actuator/**").permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer((oauth2 -> oauth2
                .jwt(customizer -> customizer.jwtAuthenticationConverter(roleConverter))));
        //http.addFilterAfter(createPolicyEnforcerFilter(), BearerTokenAuthenticationFilter.class);
        http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler methodSecurity() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler ();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix ("");
        return defaultMethodSecurityExpressionHandler;
    }


















//    @Value("${keycloak.server-url}")
//    String SERVER_URL;
//
//    @Value("${keycloak.realm}")
//    String REALM;
//
//    @Value("${keycloak.client-id}")
//    String RESOURCE;
//
//    @Value("${keycloak.client-secret}")
//    String CLIENT_SECRET;
//
//    private ServletPolicyEnforcerFilter createPolicyEnforcerFilter() {
//
//        PolicyEnforcerConfig config = new PolicyEnforcerConfig();
//
//        PolicyEnforcerConfig.PathConfig path1Config = new PolicyEnforcerConfig.PathConfig();
//        path1Config.setPath("/swagger-ui/*");
//        path1Config.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.DISABLED);
//
//        PolicyEnforcerConfig.PathConfig path2Config = new PolicyEnforcerConfig.PathConfig();
//        path2Config.setPath("/v3/api-docs/*");
//        path2Config.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.DISABLED);
//
//        PolicyEnforcerConfig.PathConfig path3Config = new PolicyEnforcerConfig.PathConfig();
//        path3Config.setPath("/actuator/*");
//        path3Config.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.DISABLED);
//
//        config.setAuthServerUrl(SERVER_URL);
//        config.setRealm(REALM);
//        config.setResource(RESOURCE);
//        config.setCredentials(Map.of("secret", CLIENT_SECRET));
//        config.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.ENFORCING);
//        config.setPaths(List.of(path1Config, path2Config, path3Config));
//
//        return new ServletPolicyEnforcerFilter(request -> config);
//    }

//    private ServletPolicyEnforcerFilter createPolicyEnforcerFilter() {
//        return new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
//            @Override
//            public PolicyEnforcerConfig resolve(HttpRequest request) {
//                try {
//                    return JsonSerialization.readValue(getClass()
//                                    .getResourceAsStream("/policy-enforcer.json"),
//                            PolicyEnforcerConfig.class);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
}