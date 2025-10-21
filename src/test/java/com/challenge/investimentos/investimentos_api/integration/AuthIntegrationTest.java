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
        private com.challenge.investimentos.investimentos_api.repository.UsuarioRepository usuarioRepository;

        @org.junit.jupiter.api.BeforeEach
        void cleanDatabase() {
                usuarioRepository.deleteAll();
        }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fluxoCompletoAutenticacao_registro_login_acessoProtegido() throws Exception {
        // 1. Registrar usuário
        com.challenge.investimentos.investimentos_api.dto.RegisterRequest regReq = new com.challenge.investimentos.investimentos_api.dto.RegisterRequest();
        regReq.setUsername("testuser");
        regReq.setPassword("testpass");
        regReq.setNome("Test User");
        regReq.setEmail("test@example.com");
        regReq.setCpf("12345678909");
        regReq.setRole(com.challenge.investimentos.investimentos_api.enums.RoleEnum.USER);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(regReq)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário criado com sucesso. Role: USER"));

        // 2. Fazer login e obter token
        AuthRequest loginRequest = new AuthRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpass");

        String loginResponse = mockMvc.perform(post("/api/auth/login")
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
                .andExpect(status().isForbidden());
    }

    @Test
    void login_credenciaisInvalidas_retorna401() throws Exception {
        String loginPayload = "{\"username\":\"inexistente\",\"password\":\"senhaerrada\"}";
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginPayload))
                .andExpect(status().isUnauthorized());
    }
}