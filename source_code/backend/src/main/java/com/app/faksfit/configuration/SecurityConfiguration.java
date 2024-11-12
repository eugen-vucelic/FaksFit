package com.app.faksfit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

//FOR TESTING
@Configuration
@PropertySource("file:./secured.properties")
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

//        http.cors(Customizer.withDefaults()) // disable this line to reproduce the CORS 401
//                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                        .permitAll())
//                .csrf(AbstractHttpConfigurer::disable);

        http.cors(Customizer.withDefaults());

        http
            .authorizeRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/student/register").permitAll()
                    .anyRequest().authenticated() // All other endpoints require authentication
            )
            .oauth2Login(oauth2 -> oauth2
                    .defaultSuccessUrl("http://localhost:5173/registracija", true) // Redirect after successful login
                    .failureUrl("http://localhost:5173/dashboard?error=true") // Redirect after failed login
    //                .userInfoEndpoint(userInfo -> userInfo
    //                        .oidcUserService(new OidcUserService()) // Configure user info service
    //                )
            )
            .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for testing; reconsider enabling for production
//                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll())
//                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    // CorsFilter Bean za dodatnu CORS konfiguraciju
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173"); // ili "*" za sve domene
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

