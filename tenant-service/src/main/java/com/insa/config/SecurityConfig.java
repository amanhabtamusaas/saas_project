package com.insa.config;//package com.insa.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//   // @Autowired
//   // private JwtAuthConverter jwtAuthConverter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf (AbstractHttpConfigurer::disable);
//        http.authorizeHttpRequests (authorize ->
//                authorize
//                        .requestMatchers ("/swagger-ui/**", "/v3/api-docs/**").permitAll ()
//                        .requestMatchers ("/api/**").permitAll ()
//                        .requestMatchers (HttpMethod.GET, "/api/tenants/get/**").permitAll ()
//                        .requestMatchers (HttpMethod.GET, "/api/departments/get/tenant/**").permitAll ()
//                        .requestMatchers (HttpMethod.GET, "/api/pay-grades/get/tenant/**").permitAll ()
//                        .requestMatchers (HttpMethod.GET, "/api/job-registrations/get/tenant/**").permitAll ()
//                        .anyRequest ().authenticated ());
//        http.oauth2ResourceServer (oauth2 ->
////                oauth2.jwt (configurer ->
////                        configurer.jwtAuthenticationConverter (jwtAuthConverter)));
//                oauth2.jwt (Customizer.withDefaults ()));
//        http.sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build ();
//    }
//
//    @Bean
//    public DefaultMethodSecurityExpressionHandler methodSecurity() {
//        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
//                new DefaultMethodSecurityExpressionHandler ();
//        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix ("");
//        return defaultMethodSecurityExpressionHandler;
//    }
//
//    @Bean
//    public JwtAuthenticationConverter converter() {
//        JwtAuthenticationConverter con = new JwtAuthenticationConverter ();
//        JwtGrantedAuthoritiesConverter jwtCon = new JwtGrantedAuthoritiesConverter ();
//        jwtCon.setAuthorityPrefix (""); // Default "SCOPE"
//        jwtCon.setAuthoritiesClaimName ("roles"); // Default "scope", "scp"
//        con.setJwtGrantedAuthoritiesConverter (jwtCon);
//        return con;
//    }
//}
