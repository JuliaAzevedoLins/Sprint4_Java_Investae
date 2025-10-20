package com.challenge.investimentos.investimentos_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * DTO de resposta para autenticação (login) de usuário.
 *
 * Contém o token JWT, username e role retornados após login bem-sucedido.
 */
public class AuthResponse {
    
    @Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "Nome do usuário", example = "admin")
    private String username;
    
    @Schema(description = "Role do usuário", example = "ADMIN")
    private String role;


    /**
     * Construtor padrão.
     */
    public AuthResponse() {}


    /**
     * Construtor com token JWT.
     *
     * param token token JWT gerado
     */
    public AuthResponse(String token) {
        this.token = token;
    }


    /**
     * Construtor com todos os campos.
     *
     * param token token JWT
     * param username nome do usuário
     * param role perfil do usuário
     */
    public AuthResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }


    /**
     * Obtém o token JWT.
     * @return token JWT
     */
    public String getToken() {
        return token;
    }


    /**
     * Define o token JWT.
     * @param token token JWT
     */
    public void setToken(String token) {
        this.token = token;
    }


    /**
     * Obtém o nome do usuário.
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Define o nome do usuário.
     * @param username nome do usuário
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Obtém o perfil (role) do usuário.
     * @return role
     */
    public String getRole() {
        return role;
    }


    /**
     * Define o perfil (role) do usuário.
     * @param role perfil do usuário
     */
    public void setRole(String role) {
        this.role = role;
    }
}
