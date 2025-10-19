package com.challenge.investimentos.investimentos_api.service;

import com.challenge.investimentos.investimentos_api.model.UsuarioInvestimento;
import com.challenge.investimentos.investimentos_api.repository.UsuarioInvestimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioInvestimentoServiceTest {

    @Mock
    private UsuarioInvestimentoRepository repo;

    @InjectMocks
    private UsuarioInvestimentoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarUsuarioInvestimento_success() {
        String cpf = "11144477735"; // CPF válido para teste
        when(repo.findByCpf_Cpf(cpf)).thenReturn(null);

        ResponseEntity<String> resp = service.criarUsuarioInvestimento(cpf);

        assertTrue(resp.getStatusCode().is2xxSuccessful());
        verify(repo, times(1)).save(any(UsuarioInvestimento.class));
    }
}
