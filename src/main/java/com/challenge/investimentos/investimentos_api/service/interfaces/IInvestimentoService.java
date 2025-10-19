package com.challenge.investimentos.investimentos_api.service.interfaces;

import com.challenge.investimentos.investimentos_api.dto.UsuarioInvestimentoDTO;
import com.challenge.investimentos.investimentos_api.model.Investimento;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface para operações de investimentos.
 * Segue o princípio da inversão de dependência (SOLID) e facilita testes.
 */
public interface IInvestimentoService {
    
    ResponseEntity<String> salvarInvestimentos(UsuarioInvestimentoDTO dto);
    
    ResponseEntity<List<Investimento>> listarInvestimentos();
    
    ResponseEntity<List<Investimento>> listarInvestimentosPorCpf(String cpf);
    
    ResponseEntity<String> atualizarInvestimento(Long id, UsuarioInvestimentoDTO dto);
    
    ResponseEntity<String> deletarInvestimento(Long id);
}