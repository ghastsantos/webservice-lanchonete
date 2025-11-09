package br.pucpr.lanchonete.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador gerado automaticamente")
    private Long id;

    @NotNull(message = "clienteId é obrigatório")
    private Long clienteId;

    private String status;

    @Valid
    @NotEmpty(message = "itens são obrigatórios")
    private List<Item> itens = new ArrayList<>();

    @Getter
    @Setter
    public static class Item {
        @NotNull(message = "produtoId é obrigatório")
        private Long produtoId;

        @NotNull(message = "quantidade é obrigatória")
        @Min(value = 1, message = "quantidade mínima é 1")
        private Integer quantidade;
    }
}

