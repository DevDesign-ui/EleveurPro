package com.eleveurpro.service;

import com.eleveurpro.dto.*;
import com.eleveurpro.entity.User;
import com.eleveurpro.entity.enums.Role;
import com.eleveurpro.exception.ResourceAlreadyExistsException;
import com.eleveurpro.exception.UnauthorizedException;
import com.eleveurpro.repository.UserRepository;
import com.eleveurpro.security.CustomUserDetailsService;
import com.eleveurpro.security.JwtService;
import com.eleveurpro.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private CustomUserDetailsService customUserDetailsService;

    @InjectMocks private AuthServiceImpl authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .nom("Test")
                .prenom("User")
                .email("test@test.com")
                .password("encoded")
                .role(Role.ELEVEUR)
                .actif(true)
                .build();
    }

    @Test
    void register_shouldReturnTokenAndUser() {
        RegisterRequest request = RegisterRequest.builder()
                .nom("Test").prenom("User").email("test@test.com")
                .password("password123").role(Role.ELEVEUR).build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(mockUserDetails);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("test@test.com", response.getUser().getEmail());
        assertEquals(Role.ELEVEUR, response.getUser().getRole());
    }

    @Test
    void register_shouldThrowWhenEmailExists() {
        RegisterRequest request = RegisterRequest.builder()
                .nom("Test").prenom("User").email("test@test.com")
                .password("password123").role(Role.ELEVEUR).build();

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    void login_shouldReturnTokenAndUser() {
        LoginRequest request = LoginRequest.builder()
                .email("test@test.com").password("password123").build();

        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("test@test.com");

        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(mockUserDetails, null, java.util.Collections.emptyList()));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(mockUserDetails);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("test@test.com", response.getUser().getEmail());
    }

    @Test
    void login_shouldThrowOnBadCredentials() {
        LoginRequest request = LoginRequest.builder()
                .email("test@test.com").password("wrong").build();

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(UnauthorizedException.class, () -> authService.login(request));
    }

    @Test
    void getCurrentUser_shouldReturnUser() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));

        UserResponse response = authService.getCurrentUser("test@test.com");

        assertNotNull(response);
        assertEquals("test@test.com", response.getEmail());
        assertEquals(Role.ELEVEUR, response.getRole());
    }
}
