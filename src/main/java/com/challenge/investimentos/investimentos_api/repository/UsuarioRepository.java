package com.challenge.investimentos.investimentos_api.repository;

import com.challenge.investimentos.investimentos_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para operações de acesso a dados da entidade {@link Usuario}.
 *
 * Fornece métodos para buscar usuários por username, email e CPF.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo nome de usuário.
     * param username nome de usuário
     * return usuário correspondente ou null se não encontrado
     */
    Usuario findByUsername(String username);

    /**
     * Busca um usuário pelo email.
     * param email email do usuário
     * return usuário correspondente ou null se não encontrado
     */
    Usuario findByEmail(String email);

    /**
     * Busca um usuário pelo CPF.
     * param cpf CPF do usuário
     * return usuário correspondente ou null se não encontrado
     */
    Usuario findByCpf(String cpf);
}
