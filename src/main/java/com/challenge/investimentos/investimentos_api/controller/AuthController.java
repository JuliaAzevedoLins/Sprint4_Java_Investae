package com.challenge.investimentos.investimentos_api.controller;

import com.challenge.investimentos.investimentos_api.dto.AuthRequest;
import com.challenge.investimentos.investimentos_api.dto.AuthResponse;
import com.challenge.investimentos.investimentos_api.dto.RegisterRequest;
import com.challenge.investimentos.investimentos_api.model.Usuario;
import com.challenge.investimentos.investimentos_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



/**
 * Controller responsável pelos endpoints de autenticação e registro de usuários.
 *
 * Fornece operações para registro, login (JWT) e listagem de usuários (admin).
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthController {

    private final AuthService authService;


    /**
     * Construtor para injeção do serviço de autenticação.
     *
     * param authService serviço de autenticação
     */
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    /**
     * Endpoint para registrar um novo usuário no sistema.
     *
     * param req dados do usuário a ser registrado
     * return 200 se criado, 400 se já existe ou dados inválidos, 500 para erro interno
     */
    @PostMapping("/register")
    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário no sistema com username e senha. Apenas usuários comuns podem ser registrados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Usuário já existe ou dados inválidos")
    })
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        try {
            Usuario usuario = authService.register(req);
            return ResponseEntity.ok("Usuário criado com sucesso. Role: " + usuario.getRole());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro no cadastro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }


    /**
     * Endpoint para autenticação de usuário (login).
     *
     * param req dados de autenticação (username e senha)
     * return 200 com token JWT, 401 se credenciais inválidas
     */
    @PostMapping("/login")
    @Operation(
        summary = "Fazer login",
        description = "Autentica o usuário e retorna um token JWT com informações do usuário"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, token JWT retornado"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest req) {
        try {
            AuthResponse response = authService.login(req);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }


    /**
     * Endpoint para listar todos os usuários do sistema (apenas para administradores).
     *
     * return 200 com lista de usuários, 403 se acesso negado, 500 para erro interno
     */
    @GetMapping("/users")
    @Operation(
        summary = "Listar todos os usuários (Apenas Admin)",
        description = "Retorna lista de todos os usuários do sistema. Acesso restrito para administradores."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado - apenas admins")
    })
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(authService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao carregar usuários: " + e.getMessage());
        }
    }

}
