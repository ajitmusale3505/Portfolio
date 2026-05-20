package com.edunest.security;

import com.edunest.entity.User;
import com.edunest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * UserDetailsServiceImpl
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/security/UserDetailsServiceImpl.java
 *
 * TWO responsibilities:
 *
 *  1. loadUserByUsername(email)
 *     — called by Spring Security during LOGIN
 *     — finds user by email, returns UserDetails
 *
 *  2. loadUserById(userId)
 *     — called by JwtAuthFilter on EVERY subsequent request
 *     — finds user by their ID (stored in JWT subject)
 *     — this is why @AuthenticationPrincipal gives you userId
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // ── Called during LOGIN — Spring Security uses this ───────────────
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email: " + email
                ));

        return buildUserDetails(user);
    }

    // ── Called by JwtAuthFilter — loads user by userId from JWT ──────
    @Transactional(readOnly = true)
    public UserDetails loadUserById(String userId) {
        log.debug("Loading user by id: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with id: " + userId
                ));

        return buildUserDetails(user);
    }

    // ── Shared builder ────────────────────────────────────────────────
    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())                 // userId is the "username" in UserDetails
                .password(user.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                        // Produces:  ROLE_STUDENT  /  ROLE_ADMIN  /  ROLE_MODERATOR
                ))
                .accountLocked(!user.getIsActive())     // locked if account disabled
                .disabled(!user.getIsActive())
                .accountExpired(false)
                .credentialsExpired(false)
                .build();
    }
}