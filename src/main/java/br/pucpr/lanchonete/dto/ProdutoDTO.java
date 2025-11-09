package br.pucpr.lanchonete.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador gerado automaticamente")
    private Long id;

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "categoriaId é obrigatório")
    private Long categoriaId;
}

