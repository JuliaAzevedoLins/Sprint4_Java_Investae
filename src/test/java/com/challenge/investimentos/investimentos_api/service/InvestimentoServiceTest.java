package com.challenge.investimentos.investimentos_api.service;

import com.challenge.investimentos.investimentos_api.dto.UsuarioInvestimentoDTO;
import com.challenge.investimentos.investimentos_api.model.UsuarioInvestimento;
import com.challenge.investimentos.investimentos_api.repository.UsuarioInvestimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para InvestimentoService.
 * Demonstra uso de mocks para isolar dependências e testar regras de negócio.
 */
class InvestimentoServiceTest {

    @Mock
    private UsuarioInvestimentoRepository usuarioRepository;

    @InjectMocks
    private InvestimentoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarInvestimentos_success() {
        // Given
        UsuarioInvestimento usuario = new UsuarioInvestimento();
        usuario.setCpfIdentificacao("11144477735"); // CPF válido
        when(usuarioRepository.findByCpf_Cpf("11144477735")).thenReturn(usuario);

        // When
        ResponseEntity<List<com.challenge.investimentos.investimentos_api.model.Investimento>> response = 
            service.listarInvestimentosPorCpf("11144477735");

        // Then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        verify(usuarioRepository, times(1)).findByCpf_Cpf("11144477735");
    }

    @Test
    void listarInvestimentosPorCpf_usuarioNaoEncontrado() {
        // Given
        when(usuarioRepository.findByCpf_Cpf("99999999999")).thenReturn(null);

        // When
        ResponseEntity<List<com.challenge.investimentos.investimentos_api.model.Investimento>> response = 
            service.listarInvestimentosPorCpf("99999999999");

        // Then
        assertTrue(response.getStatusCode().is4xxClientError());
        verify(usuarioRepository, times(1)).findByCpf_Cpf("99999999999");
    }

    @Test
    void salvarInvestimentos_cpfVazio_retornaBadRequest() {
        // Given
        UsuarioInvestimentoDTO dto = new UsuarioInvestimentoDTO();
        dto.setCpfIdentificacao("");

        // When
        ResponseEntity<String> response = service.salvarInvestimentos(dto);

        // Then
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNotNull(response.getBody());
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("CPF"));
        verify(usuarioRepository, never()).save(any());
    }
}