package com.challenge.investimentos.investimentos_api.controller;

import com.challenge.investimentos.investimentos_api.dto.TipoInvestimentoDTO;
import com.challenge.investimentos.investimentos_api.service.TipoInvestimentoService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

/**
 * Controller responsável pelos endpoints relacionados a tipos de investimento.
 */
@RestController
@RequestMapping("/api/tipos-investimento")
@Tag(name = "Tipos de Investimento", description = "Endpoints para operações relacionadas a tipos de investimento")
public class TipoInvestimentoController {

    private final TipoInvestimentoService tipoInvestimentoService;

    /**
     * Construtor para injeção do serviço de tipos de investimento.
     * @param tipoInvestimentoService serviço de tipos de investimento
     */
    public TipoInvestimentoController(TipoInvestimentoService tipoInvestimentoService) {
        this.tipoInvestimentoService = tipoInvestimentoService;
    }

    /**
     * Lista os tipos de investimento associados ao CPF informado.
     *
     * @param cpf CPF do usuário investidor
     * @return Lista de tipos de investimento associados ao CPF
     */
    @Operation(
        summary = "Lista tipos de investimento por CPF",
        description = "Retorna uma lista de tipos de investimento associados ao CPF informado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tipos de investimento retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum tipo de investimento encontrado para o CPF informado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{cpf}")
    public List<TipoInvestimentoDTO> listarTiposPorCpf(@PathVariable String cpf) {
        return tipoInvestimentoService.listarTiposPorCpf(cpf);
    }
}