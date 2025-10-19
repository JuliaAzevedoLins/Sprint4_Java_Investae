package com.challenge.investimentos.investimentos_api.dto;

import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterRequest {
    
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome de usuário único", example = "joaosilva")
    private String username;
    
    @NotBlank(message = "Password é obrigatório")
    @Size(min = 6, message = "Password deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String password;
    
    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;
    
    @Email(message = "Email deve ser válido")
    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;
    
    @Schema(description = "Role do usuário no sistema", example = "USER", allowableValues = {"USER", "ADMIN"})
    private RoleEnum role = RoleEnum.USER;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}