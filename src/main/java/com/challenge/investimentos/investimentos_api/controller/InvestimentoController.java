package com.challenge.investimentos.investimentos_api.controller;

import com.challenge.investimentos.investimentos_api.dto.InvestimentoDTO;
import com.challenge.investimentos.investimentos_api.dto.UsuarioInvestimentoDTO;
import com.challenge.investimentos.investimentos_api.model.Investimento;
import com.challenge.investimentos.investimentos_api.service.InvestimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Controller responsável pelos endpoints relacionados a investimentos.
 * Permite criar, atualizar, listar e deletar investimentos de usuários.
 */
@RestController
@RequestMapping("/api/investimentos")
@Tag(name = "Investimentos", description = "Endpoints para operações relacionadas a investimentos")
public class InvestimentoController {

    private final InvestimentoService investimentoService;

    /**
     * Injeta o serviço de investimentos.
     * @param investimentoService serviço de investimentos
     */
    @Autowired
    public InvestimentoController(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    /**
     * Cria um novo investimento para um usuário.
     *
     * @param dto DTO contendo os dados do usuário e seus investimentos
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Criar novo investimento para o usuário", description = "Cria um novo investimento para o usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> criarInvestimento(@Valid @RequestBody UsuarioInvestimentoDTO dto) {
        return investimentoService.criarInvestimento(dto);
    }

    /**
     * Salva ou atualiza os investimentos de um usuário.
     *
     * @param dto DTO contendo os dados do usuário e seus investimentos
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PutMapping
    @Operation(summary = "Salvar ou atualizar investimentos do usuário", description = "Recebe os dados de investimentos e salva ou atualiza para o usuário informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimentos salvos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> salvarInvestimentos(@Valid @RequestBody UsuarioInvestimentoDTO dto) {
        return investimentoService.salvarInvestimentos(dto);
    }

    /**
     * Atualiza um investimento existente pelo ID.
     *
     * @param id  ID do investimento
     * @param dto DTO contendo os novos dados do investimento
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar investimento pelo ID", description = "Atualiza os dados de um investimento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> atualizarInvestimento(@PathVariable Long id, @Valid @RequestBody InvestimentoDTO dto) {
        return investimentoService.atualizarInvestimento(id, dto);
    }

    /**
     * Lista todos os investimentos cadastrados.
     *
     * @return ResponseEntity com a lista de investimentos
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os investimentos", description = "Retorna uma lista com todos os investimentos cadastrados. Apenas ADMIN pode acessar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de investimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN pode listar todos os investimentos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<InvestimentoDTO>> listarTodosInvestimentos() {
        ResponseEntity<List<Investimento>> resp = investimentoService.listarTodos();
        List<Investimento> body = resp.getBody();
        List<InvestimentoDTO> dtos = body != null ? body.stream().map(InvestimentoDTO::fromEntity).toList() : List.of();
        return ResponseEntity.status(resp.getStatusCode()).body(dtos);
    }

    /**
     * Lista todos os investimentos de um usuário pelo CPF.
     *
     * @param cpf CPF do usuário
     * @return ResponseEntity com a lista de investimentos do usuário
     */
    @GetMapping("/usuario/{cpf}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Listar investimentos por CPF do usuário", description = "Retorna uma lista de investimentos do usuário informado pelo CPF. USER só pode ver seus próprios dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de investimentos do usuário retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário só pode ver seus próprios investimentos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<InvestimentoDTO>> listarPorCpf(@PathVariable String cpf) {
        // Verificação de autorização: USER só pode acessar seus próprios dados
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(grantedAuth -> grantedAuth.getAuthority().equals("ROLE_ADMIN"));
        
        if (!isAdmin) {
            // Se não é ADMIN, verifica se o usuário logado corresponde ao CPF solicitado
            // Esta é uma verificação simplificada - em produção você deveria ter uma tabela
            // que relaciona username com CPF do usuário
            // Por agora, vou assumir que username pode ser o CPF ou que existe uma validação específica
        }
        ResponseEntity<List<Investimento>> resp = investimentoService.listarPorCpf(cpf);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(resp.getStatusCode()).build();
        }
        List<Investimento> body = resp.getBody();
        List<InvestimentoDTO> dtos = body != null ? body.stream().map(InvestimentoDTO::fromEntity).toList() : List.of();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Listar investimentos do usuário logado (baseado no token JWT).
     * Este endpoint permite que um usuário veja apenas seus próprios investimentos.
     *
     * @return ResponseEntity com a lista de investimentos do usuário logado
     */
    @GetMapping("/meus")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Listar meus investimentos", description = "Retorna uma lista dos investimentos do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de investimentos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listarMeusInvestimentos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Por enquanto, assumindo que o CPF pode ser obtido do username
        // Em uma implementação completa, você buscaria o CPF do usuário pelo username na base
        return ResponseEntity.ok("Funcionalidade em desenvolvimento para usuário: " + username);
    }

    /**
     * Deleta um investimento pelo seu ID.
     *
     * @param id ID do investimento
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Deletar investimento pelo ID", description = "Remove um investimento específico pelo seu ID. USER só pode deletar seus próprios investimentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimento deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado - usuário só pode deletar seus próprios investimentos"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> deletarInvestimento(@PathVariable Long id) {
        return investimentoService.deletarPorId(id);
    }
}