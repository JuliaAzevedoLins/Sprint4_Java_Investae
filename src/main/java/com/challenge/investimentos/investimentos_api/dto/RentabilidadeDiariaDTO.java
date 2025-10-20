package com.challenge.investimentos.investimentos_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import com.challenge.investimentos.investimentos_api.model.RentabilidadeDiaria;
import java.time.format.DateTimeFormatter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO que representa os dados de rentabilidade diária de um investimento.
 * Inclui data, valor diário da ação, taxa diária e montante acumulado diário.
 */
public class RentabilidadeDiariaDTO {

    /** Data da rentabilidade diária no formato "dd-MM-yyyy". */
    @Schema(example = "01-01-2025")
    @NotBlank(message = "dataRentabilidadeDiaria é obrigatória")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "dataRentabilidadeDiaria deve estar no formato dd-MM-yyyy")
    private String dataRentabilidadeDiaria;

    /** Valor diário da ação. */
    @Schema(example = "100.50")
    @NotNull(message = "valorDiarioAcao é obrigatório")
    private BigDecimal valorDiarioAcao;

    /** Taxa de rentabilidade diária. */
    @Schema(example = "0.12")
    @NotNull(message = "taxaDiarioRentabilidade é obrigatório")
    private BigDecimal taxaDiarioRentabilidade;

    /** Montante acumulado diário. */
    @Schema(example = "1050.75")
    @NotNull(message = "montanteAcumuladoDiario é obrigatório")
    private BigDecimal montanteAcumuladoDiario;

    // Getters e Setters

    /**
     * Obtém a data da rentabilidade diária no formato dd-MM-yyyy.
     * return data da rentabilidade diária
     */
    public String getDataRentabilidadeDiaria() {
        return dataRentabilidadeDiaria;
    }


    /**
     * Define a data da rentabilidade diária.
     * param dataRentabilidadeDiaria data no formato dd-MM-yyyy
     */
    public void setDataRentabilidadeDiaria(String dataRentabilidadeDiaria) {
        this.dataRentabilidadeDiaria = dataRentabilidadeDiaria;
    }


    /**
     * Obtém o valor diário da ação.
     * return valor diário da ação
     */
    public BigDecimal getValorDiarioAcao() {
        return valorDiarioAcao;
    }


    /**
     * Define o valor diário da ação.
     * param valorDiarioAcao valor diário da ação
     */
    public void setValorDiarioAcao(BigDecimal valorDiarioAcao) {
        this.valorDiarioAcao = valorDiarioAcao;
    }


    /**
     * Obtém a taxa de rentabilidade diária.
     * return taxa de rentabilidade diária
     */
    public BigDecimal getTaxaDiarioRentabilidade() {
        return taxaDiarioRentabilidade;
    }


    /**
     * Define a taxa de rentabilidade diária.
     * param taxaDiarioRentabilidade taxa de rentabilidade diária
     */
    public void setTaxaDiarioRentabilidade(BigDecimal taxaDiarioRentabilidade) {
        this.taxaDiarioRentabilidade = taxaDiarioRentabilidade;
    }


    /**
     * Obtém o montante acumulado diário.
     * return montante acumulado diário
     */
    public BigDecimal getMontanteAcumuladoDiario() {
        return montanteAcumuladoDiario;
    }


    /**
     * Define o montante acumulado diário.
     * param montanteAcumuladoDiario montante acumulado diário
     */
    public void setMontanteAcumuladoDiario(BigDecimal montanteAcumuladoDiario) {
        this.montanteAcumuladoDiario = montanteAcumuladoDiario;
    }

    /**
     * Converte uma entidade {@link RentabilidadeDiaria} para um DTO {@link RentabilidadeDiariaDTO}.
     *
     * param rd entidade de rentabilidade diária
     * return DTO populado com os dados da entidade
     */
    public static RentabilidadeDiariaDTO fromEntity(RentabilidadeDiaria rd) {
        if (rd == null) return null;
        RentabilidadeDiariaDTO dto = new RentabilidadeDiariaDTO();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dto.setDataRentabilidadeDiaria(rd.getDataRentabilidadeDiaria() != null ? rd.getDataRentabilidadeDiaria().format(fmt) : null);
        dto.setValorDiarioAcao(rd.getValorDiarioAcao());
        dto.setTaxaDiarioRentabilidade(rd.getTaxaDiarioRentabilidade());
        dto.setMontanteAcumuladoDiario(rd.getMontanteAcumuladoDiario());
        return dto;
    }
}
