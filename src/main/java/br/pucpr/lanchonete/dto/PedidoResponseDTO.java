package br.pucpr.lanchonete.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador do pedido")
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private String status;
    private LocalDateTime dataCriacao;
    private List<Item> itens = new ArrayList<>();

    @Getter
    @Setter
    public static class Item {
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador do item")
        private Long id;
        private Long produtoId;
        private String produtoNome;
        private Integer quantidade;
        private java.math.BigDecimal precoUnitario;
    }
}

