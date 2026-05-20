package com.edunest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthFilter
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/security/JwtAuthFilter.java
 *
 * Runs ONCE per request.
 *
 * Flow:
 *   Request → Extract "Bearer <token>" from header
 *           → Validate token signature + expiry
 *           → Extract userId from token
 *           → Load user from DB by userId
 *           → Set authentication in SecurityContext
 *           → Continue to Controller
 *
 * After this filter:
 *   @AuthenticationPrincipal UserDetails userDetails
 *   userDetails.getUsername()  →  returns userId e.g. "USER1000"
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils               jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest  request,
                                    HttpServletResponse response,
                                    FilterChain         filterChain)
            throws ServletException, IOException {

        try {
            // 1. Extract JWT token from Authorization header
            String jwt = extractJwt(request);

            if (jwt != null && jwtUtils.validateToken(jwt)) {

                // 2. Get userId stored as subject in token
                String userId = jwtUtils.getUserIdFromToken(jwt);

                // 3. Load full UserDetails from DB using userId
                UserDetails userDetails = userDetailsService.loadUserById(userId);

                // 4. Create authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,            // principal
                                null,                   // credentials (not needed after login)
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 5. Store in SecurityContext — now request is authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Authenticated user: {}", userId);
            }

        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            // Don't throw — let the request continue, SecurityContext stays empty
            // SecurityConfig will return 401 for protected routes
        }

        filterChain.doFilter(request, response);
    }

    // ── Parse "Bearer <token>" from Authorization header ─────────────
    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}