package com.challenge.investimentos.investimentos_api.service;

import com.challenge.investimentos.investimentos_api.dto.AuthRequest;
import com.challenge.investimentos.investimentos_api.dto.AuthResponse;
import com.challenge.investimentos.investimentos_api.dto.RegisterRequest;
import com.challenge.investimentos.investimentos_api.enums.RoleEnum;
import com.challenge.investimentos.investimentos_api.model.Usuario;
import com.challenge.investimentos.investimentos_api.repository.UsuarioRepository;
import com.challenge.investimentos.investimentos_api.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementação do serviço de autenticação.
 * Implementa regras de negócio para registro de usuários, login e validação de senhas.
 * Segue os princípios SOLID:
 * - Single Responsibility: apenas operações de autenticação
 * - Dependency Inversion: depende de interfaces/abstrações
 */
@Service
public class AuthService implements com.challenge.investimentos.investimentos_api.service.interfaces.IAuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final com.challenge.investimentos.investimentos_api.repository.UsuarioInvestimentoRepository usuarioInvestimentoRepository;

    /**
     * Construtor para injeção de dependências.
     */
    public AuthService(UsuarioRepository usuarioRepository, 
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager,
                      JwtTokenProvider jwtTokenProvider,
                      com.challenge.investimentos.investimentos_api.repository.UsuarioInvestimentoRepository usuarioInvestimentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioInvestimentoRepository = usuarioInvestimentoRepository;
    }

    /**
     * Registra um novo usuário no sistema.
     * @param request Dados do usuário para registro
     * @return Usuario criado
     * @throws IllegalArgumentException se username ou email já existir
     */
    public Usuario register(RegisterRequest request) {
        // Verifica se username já existe
        if (usuarioRepository.findByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException("Username já existe");
        }

        // Verifica se email já existe (se fornecido)
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (usuarioRepository.findByEmail(request.getEmail()) != null) {
                throw new IllegalArgumentException("Email já está em uso");
            }
        }

        // Verifica se CPF já existe
        if (usuarioRepository.findByCpf(request.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

    // Cria novo usuário
    Usuario usuario = new Usuario();
    usuario.setUsername(request.getUsername());
    usuario.setPassword(passwordEncoder.encode(request.getPassword()));
    usuario.setNome(request.getNome());
    usuario.setEmail(request.getEmail());
    usuario.setCpf(request.getCpf());
    usuario.setRole(request.getRole() != null ? request.getRole() : RoleEnum.USER);

    Usuario savedUser = usuarioRepository.save(usuario);

    // Cria automaticamente o registro de USUARIO_INVESTIMENTO com o mesmo CPF
    // Só cria se ainda não existir
    // Repositório precisa ser injetado
    if (usuarioInvestimentoRepository.findByCpf_Cpf(request.getCpf()) == null) {
        com.challenge.investimentos.investimentos_api.model.UsuarioInvestimento investidor = new com.challenge.investimentos.investimentos_api.model.UsuarioInvestimento();
        investidor.setCpfIdentificacao(request.getCpf());
        usuarioInvestimentoRepository.save(investidor);
    }

    return savedUser;
    }

    /**
     * Realiza login do usuário.
     * @param request Credenciais de login
     * @return AuthResponse com token JWT
     */
    public AuthResponse login(AuthRequest request) {
        // Autentica usuário
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // Busca dados do usuário
        Usuario usuario = usuarioRepository.findByUsername(auth.getName());
        if (usuario == null) {
            throw new IllegalStateException("Usuário não encontrado após autenticação");
        }

        // Gera token JWT
        String token = jwtTokenProvider.createToken(usuario.getUsername(), usuario.getRoleAsString());

        return new AuthResponse(token, usuario.getUsername(), usuario.getRoleAsString());
    }

    /**
     * Valida se uma senha raw corresponde ao hash armazenado.
     * @param rawPassword Senha em texto plano
     * @param encodedPassword Senha criptografada
     * @return true se a senha for válida
     */
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Busca usuário por username.
     * @param username Username do usuário
     * @return Usuario encontrado ou null
     */
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Retorna todos os usuários do sistema (apenas para administradores).
     * @return Lista de todos os usuários
     */
    public java.util.List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    /**
     * Salva um usuário no banco de dados.
     * @param usuario Usuario a ser salvo
     * @return Usuario salvo
     */
    public Usuario saveUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}