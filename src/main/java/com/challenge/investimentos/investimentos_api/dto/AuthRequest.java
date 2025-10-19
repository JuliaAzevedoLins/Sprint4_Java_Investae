package com.challenge.investimentos.investimentos_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequest {
    
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, message = "Username deve ter pelo menos 3 caracteres")
    @Schema(description = "Nome de usuário para login", example = "usuario123")
    private String username;
    
    @NotBlank(message = "Password é obrigatório")
    @Size(min = 6, message = "Password deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha do usuário", example = "minhasenha123")
    private String password;

    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
}
