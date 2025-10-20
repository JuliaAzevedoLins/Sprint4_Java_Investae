package com.challenge.investimentos.investimentos_api.dto;

import java.math.BigDecimal;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import com.challenge.investimentos.investimentos_api.model.Investimento;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO que representa os dados de um investimento.
 *
 * Utilizado para transferência de dados entre a API e o frontend, incluindo informações
 * como banco, tipo, valores, rentabilidade e lista de rentabilidades diárias.
 */
public class InvestimentoDTO {

    /** Identificador único do investimento. */
    @Schema(example = "1")
    private Long id;


    /** Nome do banco associado ao investimento. */
    @Schema(example = "C6 Bank")
    private String nomeBanco;


    /** Tipo do investimento (ex: RENDA_FIXA, RENDA_VARIAVEL). */
    @Schema(example = "RENDA_FIXA")
    private String tipoInvestimento;


    /** Nome do investimento. */
    @Schema(example = "CDB 2025")
    @NotBlank(message = "nomeInvestimento é obrigatório")
    private String nomeInvestimento;


    /** Valor inicial investido. */
    @Schema(example = "1000.50")
    @NotNull(message = "montanteInicial é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "montanteInicial deve ser maior que zero")
    private BigDecimal montanteInicial;


    /** Valor inicial da ação (caso aplicável). */
    @Schema(example = "100.50")
    @NotNull(message = "valorInicialAcao é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "valorInicialAcao não pode ser negativo")
    private BigDecimal valorInicialAcao;


    /** Taxa de rentabilidade do investimento. */
    @Schema(example = "0.05")
    @NotNull(message = "taxaRentabilidade é obrigatório")
    private BigDecimal taxaRentabilidade;


    /** Número inicial de ações (caso aplicável). */
    @Schema(example = "10")
    @NotNull(message = "numeroAcoesInicial é obrigatório")
    @PositiveOrZero(message = "numeroAcoesInicial deve ser zero ou maior")
    private Integer numeroAcoesInicial;


    /** Lista de rentabilidades diárias associadas ao investimento. */
    @Schema(description = "Lista de rentabilidades diárias associadas ao investimento")
    @Valid
    private List<RentabilidadeDiariaDTO> rentabilidadeDiaria;

    // Getters e Setters
    /**
     * Obtém o ID do investimento.
     * return id
     */
    public Long getId() {
        return id;
    }


    /**
     * Define o ID do investimento.
     * param id identificador
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Obtém o nome do banco.
     * return nomeBanco
     */
    public String getNomeBanco() {
        return nomeBanco;
    }


    /**
     * Define o nome do banco.
     * param nomeBanco nome do banco
     */
    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }


    /**
     * Obtém o tipo do investimento.
     * return tipoInvestimento
     */
    public String getTipoInvestimento() {
        return tipoInvestimento;
    }


    /**
     * Define o tipo do investimento.
     * param tipoInvestimento tipo do investimento
     */
    public void setTipoInvestimento(String tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }


    /**
     * Obtém o nome do investimento.
     * return nomeInvestimento
     */
    public String getNomeInvestimento() {
        return nomeInvestimento;
    }


    /**
     * Define o nome do investimento.
     * param nomeInvestimento nome do investimento
     */
    public void setNomeInvestimento(String nomeInvestimento) {
        this.nomeInvestimento = nomeInvestimento;
    }


    /**
     * Obtém o montante inicial investido.
     * return montanteInicial
     */
    public BigDecimal getMontanteInicial() {
        return montanteInicial;
    }


    /**
     * Define o montante inicial investido.
     * param montanteInicial valor investido
     */
    public void setMontanteInicial(BigDecimal montanteInicial) {
        this.montanteInicial = montanteInicial;
    }


    /**
     * Obtém o valor inicial da ação.
     * return valorInicialAcao
     */
    public BigDecimal getValorInicialAcao() {
        return valorInicialAcao;
    }


    /**
     * Define o valor inicial da ação.
     * param valorInicialAcao valor da ação
     */
    public void setValorInicialAcao(BigDecimal valorInicialAcao) {
        this.valorInicialAcao = valorInicialAcao;
    }


    /**
     * Obtém a taxa de rentabilidade.
     * return taxaRentabilidade
     */
    public BigDecimal getTaxaRentabilidade() {
        return taxaRentabilidade;
    }


    /**
     * Define a taxa de rentabilidade.
     * param taxaRentabilidade taxa de rentabilidade
     */
    public void setTaxaRentabilidade(BigDecimal taxaRentabilidade) {
        this.taxaRentabilidade = taxaRentabilidade;
    }


    /**
     * Obtém o número inicial de ações.
     * return numeroAcoesInicial
     */
    public Integer getNumeroAcoesInicial() {
        return numeroAcoesInicial;
    }


    /**
     * Define o número inicial de ações.
     * param numeroAcoesInicial número de ações
     */
    public void setNumeroAcoesInicial(Integer numeroAcoesInicial) {
        this.numeroAcoesInicial = numeroAcoesInicial;
    }


    /**
     * Obtém a lista de rentabilidades diárias.
     * return lista de rentabilidades diárias
     */
    public List<RentabilidadeDiariaDTO> getRentabilidadeDiaria() {
        return rentabilidadeDiaria;
    }


    /**
     * Define a lista de rentabilidades diárias.
     * param rentabilidadeDiaria lista de rentabilidades
     */
    public void setRentabilidadeDiaria(List<RentabilidadeDiariaDTO> rentabilidadeDiaria) {
        this.rentabilidadeDiaria = rentabilidadeDiaria;
    }

    /**
     * Converte uma entidade {@link Investimento} para um DTO {@link InvestimentoDTO}.
     *
     * param inv entidade de investimento
     * return DTO populado com os dados da entidade
     */
    public static InvestimentoDTO fromEntity(Investimento inv) {
        if (inv == null) return null;
        InvestimentoDTO dto = new InvestimentoDTO();
        dto.setId(inv.getId());
        dto.setNomeBanco(inv.getNomeBanco());
        dto.setTipoInvestimento(inv.getTipoInvestimento() != null ? inv.getTipoInvestimento().name() : null);
        dto.setNomeInvestimento(inv.getNomeInvestimento());
        dto.setMontanteInicial(inv.getMontanteInicial());
        dto.setValorInicialAcao(inv.getValorInicialAcao());
        dto.setTaxaRentabilidade(inv.getTaxaRentabilidade());
        dto.setNumeroAcoesInicial(inv.getNumeroAcoesInicial());
        if (inv.getRentabilidadeDiaria() != null) {
            dto.setRentabilidadeDiaria(inv.getRentabilidadeDiaria().stream()
                .map(RentabilidadeDiariaDTO::fromEntity)
                .collect(Collectors.toList()));
        }
        return dto;
    }
}
