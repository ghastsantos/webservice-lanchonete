package br.pucpr.lanchonete.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador gerado automaticamente")
    private Long id;

    @NotBlank(message = "logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "estado é obrigatório")
    private String estado;

    @NotBlank(message = "cep é obrigatório")
    private String cep;

    @NotNull(message = "clienteId é obrigatório")
    private Long clienteId;
}

