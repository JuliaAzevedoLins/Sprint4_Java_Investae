package com.challenge.investimentos.investimentos_api.service.interfaces;

import com.challenge.investimentos.investimentos_api.dto.UsuarioInvestimentoDTO;
import com.challenge.investimentos.investimentos_api.model.UsuarioInvestimento;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface para operações de usuários investidores.
 * Permite extensibilidade e facilita testes unitários através de mocks.
 */
public interface IUsuarioInvestimentoService {
    
    ResponseEntity<String> criarUsuarioInvestimento(String cpfIdentificacao);
    
    ResponseEntity<String> salvarInvestimentos(UsuarioInvestimentoDTO dto);
    
    ResponseEntity<List<UsuarioInvestimento>> listarTodosUsuarios();
    
    ResponseEntity<?> buscarPorCpf(String cpf);
    
    ResponseEntity<String> deletarPorCpf(String cpf);
}