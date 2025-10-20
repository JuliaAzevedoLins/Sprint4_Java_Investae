package com.challenge.investimentos.investimentos_api.model;

import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


/**
 * Entidade que representa um usuário do sistema.
 *
 * Contém informações de autenticação, identificação e perfil de acesso.
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
    

    /** Serial version UID para serialização. */
    private static final long serialVersionUID = 1L;


    /** Identificador único do usuário. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /** Nome de usuário único. */
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    private String username;


    /** Senha do usuário (armazenada criptografada com BCrypt). */
    @Column(nullable = false)
    @NotBlank(message = "Password é obrigatório")
    private String password; // armazenada criptografada com BCrypt


    /** Perfil (role) do usuário. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role = RoleEnum.USER;


    /** Nome completo do usuário. */
    @Column
    private String nome;


    /** Email do usuário. */
    @Column
    private String email;


    /** CPF do usuário. */
    @Column(unique = true, nullable = false)
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter entre 11 e 14 caracteres")
    private String cpf;


    /**
     * Obtém o ID do usuário.
     * return id
     */
    public Long getId() {
        return id;
    }


    /**
     * Define o ID do usuário.
     * param id identificador
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Obtém o nome de usuário.
     * return username
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
     * return senha (criptografada)
     */
    public String getPassword() {
        return password;
    }


    /**
     * Define a senha do usuário.
     * param password senha (criptografada)
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Retorna a role como string para compatibilidade com Spring Security.
     * return role como string
     */
    public String getRoleAsString() {
        return role != null ? role.getRole() : RoleEnum.USER.getRole();
    }
}
