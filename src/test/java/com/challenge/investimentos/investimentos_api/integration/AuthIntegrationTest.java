package com.challenge.investimentos.investimentos_api.integration;

import com.challenge.investimentos.investimentos_api.dto.AuthRequest;
import com.challenge.investimentos.investimentos_api.dto.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para fluxo completo de autenticação.
 * Demonstra teste end-to-end com registro, login e acesso a endpoint protegido.
 * Testa autenticação JWT e autorização baseada em roles.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fluxoCompletoAutenticacao_registro_login_acessoProtegido() throws Exception {
        // 1. Registrar usuário
        AuthRequest registerRequest = new AuthRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("testpass");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário criado"));

        // 2. Fazer login e obter token
        AuthRequest loginRequest = new AuthRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpass");

        String loginResponse = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(loginResponse, AuthResponse.class);
        String token = authResponse.getToken();

        // 3. Acessar endpoint protegido com token
        mockMvc.perform(get("/api/usuario-investimentos")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        // 4. Tentar acessar sem token (deve falhar)
        mockMvc.perform(get("/api/usuario-investimentos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_credenciaisInvalidas_retorna401() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("inexistente");
        request.setPassword("senhaerrada");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}