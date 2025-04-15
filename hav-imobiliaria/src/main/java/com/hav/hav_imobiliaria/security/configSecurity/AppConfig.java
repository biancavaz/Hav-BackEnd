package com.hav.hav_imobiliaria.security.configSecurity;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppConfig {
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
        ROLE_ADMIN > ROLE_EDITOR
        ROLE_EDITOR > ROLE_REALTOR
        ROLE_REALTOR > ROLE_CUSTOMER
    """);
        return hierarchy;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**").permitAll()
                        /* ADDITIONALS CONTROLLER */
                        .requestMatchers(HttpMethod.POST, "/additionals").hasRole("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/additionals").permitAll()

                        /* ADMIN CONTROLLER */
                        .requestMatchers("/adm/**").hasRole("EDITOR")
                        /* ADDRESS CONTROLLER */
                        .requestMatchers("/address/**").hasRole("EDITOR")
                        /* EMAIL CONTROLLER */
                        .requestMatchers("/contactus").authenticated()
                        /* CUSTOMER CONTROLLER */
                        .requestMatchers("/customer/**").hasRole("EDITOR")
                        /* EDITOR CONTROLLER */
                        .requestMatchers("/editor/**").hasRole("EDITOR")
                        /* FAVORITES CONTROLLER */
                        .requestMatchers("/favorites/**").authenticated()
                        /* PROPERTY CONTROLLER */
                        .requestMatchers("/property/randomHighlights").permitAll()
                        .requestMatchers(HttpMethod.POST, "/property").hasRole("EDITOR")
                        .requestMatchers(HttpMethod.GET, "/property").hasRole("EDITOR")
                        .requestMatchers("/property/filter",
                                "/property/filter/card",
                                "/property/filter/map").authenticated()
                        .requestMatchers("/property/changeArchiveStatus").hasRole("EDITOR")
                        .requestMatchers("/property/changeArchiveStatus").hasRole("EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/property/**").hasRole("EDITOR")
                        .requestMatchers("/property/getAll",
                                "/property/getPercentageForSale",
                                "/property/getPercentageOfArchiveStatus",
                                "/property/getQuantityOfRentalProperties",
                                "/property/getQuantityOfForSaleProperties",
                                "/property/getQuantityOfArchivedProperties").hasRole("ADMIN")
                        /* PROPRIETOR CONTROLLER */
                        .requestMatchers("/proprietor/**").hasRole("EDITOR")
                        /* REALTOR CONTROLLER */
                        .requestMatchers("/realtor/**").hasRole("EDITOR")
                        /* SCHEDULES CONTROLLER */
                        .requestMatchers(HttpMethod.GET, "/schedules/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/schedules/**").hasRole("REALTOR")
                        .requestMatchers(HttpMethod.PUT, "/schedules/**").hasRole("REALTOR")
                        .requestMatchers(HttpMethod.DELETE, "/schedules/**").hasRole("REALTOR")
                        .requestMatchers(HttpMethod.PATCH, "/schedules/**").hasRole("REALTOR")
                        .requestMatchers("/schedules/history/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/schedules/history/realtor/**").hasRole("REALTOR")
                        .anyRequest().permitAll()
                        /* TAXES CONTROLLER */
                        .requestMatchers(HttpMethod.GET, "/taxes/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
