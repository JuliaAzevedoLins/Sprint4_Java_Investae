package com.challenge.investimentos.investimentos_api.enums;

/**
 * Enum para definir os diferentes tipos de roles no sistema.
 * USER: Usuário comum - pode ver apenas seus próprios investimentos
 * ADMIN: Administrador - pode ver todos os investimentos e deletar qualquer um
 */
public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}