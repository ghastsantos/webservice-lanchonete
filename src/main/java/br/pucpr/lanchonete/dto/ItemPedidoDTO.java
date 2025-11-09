package br.pucpr.lanchonete.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador gerado automaticamente")
    private Long id;

    @NotNull(message = "produtoId é obrigatório")
    private Long produtoId;

    @NotNull(message = "pedidoId é obrigatório")
    private Long pedidoId;

    @NotNull(message = "quantidade é obrigatória")
    @Min(value = 1, message = "quantidade mínima é 1")
    private Integer quantidade;

    private BigDecimal precoUnitario;
}

