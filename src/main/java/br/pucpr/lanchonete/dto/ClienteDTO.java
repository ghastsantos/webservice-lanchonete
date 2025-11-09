package br.pucpr.lanchonete.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador gerado automaticamente")
    private Long id;

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @Email(message = "email inválido")
    @NotBlank(message = "email é obrigatório")
    private String email;

    @NotBlank(message = "telefone é obrigatório")
    private String telefone;
}

