package com.challenge.investimentos.investimentos_api.dto;

import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * DTO para requisição de registro de novo usuário.
 *
 * Contém os dados necessários para criar um usuário no sistema, como username, senha, nome, email, CPF e role.
 */
public class RegisterRequest {
    

    /** Nome de usuário único. */
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome de usuário único", example = "joaosilva")
    private String username;
    

    /** Senha do usuário. */
    @NotBlank(message = "Password é obrigatório")
    @Size(min = 6, message = "Password deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String password;
    

    /** Nome completo do usuário. */
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;
    

    /** Email do usuário. */
    @Email(message = "Email deve ser válido")
    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;
    

    /** CPF do usuário. */
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    @Schema(description = "CPF do usuário", example = "12345678901")
    private String cpf;


    /** Perfil (role) do usuário no sistema. */
    @Schema(description = "Role do usuário no sistema", example = "USER", allowableValues = {"USER", "ADMIN"})
    private RoleEnum role = RoleEnum.USER;

    // Getters and Setters
    /**
     * Obtém o nome de usuário.
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Define o nome de usuário.
     * param username nome de usuário
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Obtém a senha do usuário.
     * return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Define a senha do usuário.
     * param password senha
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Obtém o nome completo do usuário.
     * return nome
     */
    public String getNome() {
        return nome;
    }


    /**
     * Define o nome completo do usuário.
     * param nome nome completo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }


    /**
     * Obtém o email do usuário.
     * return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Define o email do usuário.
     * param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Obtém o CPF do usuário.
     * return cpf
     */
    public String getCpf() {
        return cpf;
    }


    /**
     * Define o CPF do usuário.
     * param cpf CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    /**
     * Obtém o perfil (role) do usuário.
     * return role
     */
    public RoleEnum getRole() {
        return role;
    }


    /**
     * Define o perfil (role) do usuário.
     * param role perfil do usuário
     */
    public void setRole(RoleEnum role) {
        this.role = role;
    }
}