package com.edunest.config;

import com.edunest.security.JwtAuthFilter;
import com.edunest.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * SecurityConfig
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/config/SecurityConfig.java
 *
 * Configures:
 *   - Which routes are PUBLIC  (no token needed)
 *   - Which routes are PROTECTED  (token required)
 *   - Which routes are ADMIN only
 *   - JWT filter placement
 *   - CORS settings for React frontend
 *   - BCrypt password encoder
 *   - Stateless session (no cookies — JWT only)
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity           // enables @PreAuthorize on controller methods
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter         jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    // ── Public routes — NO token required ────────────────────────────
    private static final String[] PUBLIC_URLS = {
        "/api/auth/**",             // register, login, forgot-password, reset-password
        "/api/users/leaderboard",   // public leaderboard
        "/api/users/{id}",          // public profile view
        "/uploads/**",              // static file serving
        "/actuator/health",         // health check
        "/actuator/info"
    };

    // ── Main security filter chain ────────────────────────────────────
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 1. Disable CSRF — not needed for REST APIs with JWT
            .csrf(AbstractHttpConfigurer::disable)

            // 2. Enable CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 3. Route permissions
            .authorizeHttpRequests(auth -> auth

                // Public routes — anyone can access
                .requestMatchers(PUBLIC_URLS).permitAll()

                // Admin only routes
                .requestMatchers(HttpMethod.GET,    "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/users/search").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/users/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH,  "/api/users/*/status").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH,  "/api/users/*/role").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")

                // All other requests — must be authenticated (valid JWT)
                .anyRequest().authenticated()
            )

            // 4. Stateless session — no HttpSession, no cookies
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 5. Use our custom UserDetailsService + BCrypt
            .authenticationProvider(authenticationProvider())

            // 6. Add JWT filter BEFORE Spring's default login filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ── AuthenticationProvider — ties UserDetailsService + PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = 
            new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ── AuthenticationManager — used in AuthService to authenticate login
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ── BCrypt password encoder — strength 12 ────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // ── CORS — allow React frontend to call this backend ─────────────
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow your React frontend URL — change port if needed
        config.setAllowedOrigins(List.of(
            "http://localhost:3000",    // React dev server
            "http://localhost:5173"     // Vite dev server
        ));

        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        config.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "X-Requested-With"
        ));

        config.setAllowCredentials(true);
        config.setMaxAge(3600L);    // Cache preflight for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}