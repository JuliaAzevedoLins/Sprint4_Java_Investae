package com.challenge.investimentos.investimentos_api.service;

import com.challenge.investimentos.investimentos_api.dto.AuthRequest;
import com.challenge.investimentos.investimentos_api.dto.AuthResponse;
import com.challenge.investimentos.investimentos_api.dto.RegisterRequest;
import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import com.challenge.investimentos.investimentos_api.model.Usuario;
import com.challenge.investimentos.investimentos_api.repository.UsuarioRepository;
import com.challenge.investimentos.investimentos_api.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para AuthService.
 * Testa as regras de negócio de autenticação e autorização.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private com.challenge.investimentos.investimentos_api.repository.UsuarioInvestimentoRepository usuarioInvestimentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    private AuthService authService;

    @BeforeEach
    void setUp() {
    authService = new AuthService(usuarioRepository, passwordEncoder, authenticationManager, jwtTokenProvider, usuarioInvestimentoRepository);
    }

    @Test
    @DisplayName("Deve registrar usuário com sucesso")
    void register_Success() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("password123");
        request.setNome("Test User");
        request.setEmail("test@email.com");
        request.setRole(RoleEnum.USER);

        when(usuarioRepository.findByUsername("testuser")).thenReturn(null);
        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Usuario result = authService.register(request);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(RoleEnum.USER, result.getRole());
        assertEquals("Test User", result.getNome());
        assertEquals("test@email.com", result.getEmail());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    @DisplayName("Deve falhar ao registrar usuário com username já existente")
    void register_UsernameExists_ThrowsException() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");

        Usuario existingUser = new Usuario();
        existingUser.setUsername("existinguser");

        when(usuarioRepository.findByUsername("existinguser")).thenReturn(existingUser);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> authService.register(request));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve fazer login com sucesso")
    void login_Success() {
        // Given
        AuthRequest request = new AuthRequest("testuser", "password123");
        Usuario user = new Usuario();
        user.setUsername("testuser");
        user.setRole(RoleEnum.USER);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(usuarioRepository.findByUsername("testuser")).thenReturn(user);
        when(jwtTokenProvider.createToken("testuser", "USER")).thenReturn("jwt-token");

        // When
        AuthResponse response = authService.login(request);

        // Then
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("USER", response.getRole());

        verify(authenticationManager).authenticate(any());
        verify(jwtTokenProvider).createToken("testuser", "USER");
    }

    @Test
    @DisplayName("Deve falhar login com credenciais inválidas")
    void login_BadCredentials_ThrowsException() {
        // Given
        AuthRequest request = new AuthRequest("testuser", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // When & Then
        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(jwtTokenProvider, never()).createToken(anyString(), any());
    }

    @Test
    @DisplayName("Deve validar senha com BCrypt")
    void validatePassword() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = "$2a$10$encodedpassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // When
        boolean isValid = authService.validatePassword(rawPassword, encodedPassword);

        // Then
        assertTrue(isValid);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("Deve retornar false para senha inválida")
    void validatePassword_Invalid() {
        // Given
        String rawPassword = "wrongpassword";
        String encodedPassword = "$2a$10$encodedpassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // When
        boolean isValid = authService.validatePassword(rawPassword, encodedPassword);

        // Then
        assertFalse(isValid);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
    }
}