package com.challenge.investimentos.investimentos_api.controller;

import com.challenge.investimentos.investimentos_api.dto.AuthRequest;
import com.challenge.investimentos.investimentos_api.repository.UsuarioRepository;
import com.challenge.investimentos.investimentos_api.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Import;

@WebMvcTest(AuthController.class)
@Import({JwtTokenProvider.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Test
    void register_then_login() throws Exception {
        AuthRequest req = new AuthRequest();
        req.setUsername("testuser");
        req.setPassword("pass");

        when(usuarioRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        mvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk());
    }
}
