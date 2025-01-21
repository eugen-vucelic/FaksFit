package com.app.faksfit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomOAuth2SuccessHandler successHandler;
    private final boolean production = false;
    private final JWTFilter jwtfilter;

    private final String FRONTEND_URL = production ? "https://faksfit-7du1.onrender.com" : "http://localhost:5173";

    @Autowired
    public SecurityConfiguration(CustomOAuth2SuccessHandler successHandler, JWTFilter jwtfilter) {
        this.successHandler = successHandler;
        this.jwtfilter = jwtfilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/h2-console/**","/student/register", "/login**", "/error**", "/student/patch", "/oauth2/**").permitAll()
//                        .requestMatchers("/student/**").hasRole("STUDENT")
//                        .requestMatchers("/teacher/**").hasRole("TEACHER")
//                        .requestMatchers("/voditelj/**").hasRole("ACTIVITY_LEADER") #to add once jwt works on the front
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/auth/**")
                )
                .headers(headers -> headers
                        .frameOptions().sameOrigin() // Allow H2 Console to be displayed in iframe
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")  // Enables endpoint
                        .successHandler(successHandler)
                        .failureUrl(FRONTEND_URL)
                )
                .csrf(AbstractHttpConfigurer::disable);

//        http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class); #once jwt works on the front


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "https://faksfit-7du1.onrender.com",
                "http://localhost:5173",
                "http://localhost:4173"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
