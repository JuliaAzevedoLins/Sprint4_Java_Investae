package com.challenge.investimentos.investimentos_api.service.interfaces;

import com.challenge.investimentos.investimentos_api.dto.AuthRequest;
import com.challenge.investimentos.investimentos_api.dto.AuthResponse;
import com.challenge.investimentos.investimentos_api.dto.RegisterRequest;
import com.challenge.investimentos.investimentos_api.model.Usuario;

/**
 * Interface para serviços de autenticação.
 * Define contratos para operações de autenticação e autorização.
 * Aplica os princípios SOLID:
 * - Single Responsibility: responsável apenas por autenticação
 * - Interface Segregation: métodos específicos para auth
 * - Dependency Inversion: depende de abstração, não de implementação
 */
public interface IAuthService {

    /**
     * Registra um novo usuário no sistema.
     * @param request Dados do usuário para registro
     * @return Usuario criado
     * @throws IllegalArgumentException se username ou email já existir
     */
    Usuario register(RegisterRequest request);

    /**
     * Realiza login do usuário.
     * @param request Credenciais de login
     * @return AuthResponse com token JWT
     * @throws org.springframework.security.authentication.BadCredentialsException se credenciais inválidas
     */
    AuthResponse login(AuthRequest request);

    /**
     * Valida se uma senha raw corresponde ao hash armazenado.
     * @param rawPassword Senha em texto plano
     * @param encodedPassword Senha criptografada
     * @return true se a senha for válida
     */
    boolean validatePassword(String rawPassword, String encodedPassword);

    /**
     * Busca usuário por username.
     * @param username Username do usuário
     * @return Usuario encontrado ou null
     */
    Usuario findByUsername(String username);

    /**
     * Retorna todos os usuários do sistema (apenas para administradores).
     * @return Lista de todos os usuários
     */
    java.util.List<Usuario> getAllUsers();
}