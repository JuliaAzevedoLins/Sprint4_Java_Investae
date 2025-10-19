package com.challenge.investimentos.investimentos_api.repository;

import com.challenge.investimentos.investimentos_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
}
