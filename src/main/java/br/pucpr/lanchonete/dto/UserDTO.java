package br.pucpr.lanchonete.dto;

import br.pucpr.lanchonete.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Identificador gerado automaticamente")
    private Long id;

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @Email(message = "email inválido")
    @NotBlank(message = "email é obrigatório")
    private String email;

    @NotBlank(message = "senha é obrigatória")
    private String senha;

    @NotEmpty(message = "ao menos um perfil é obrigatório")
    private Set<Role> roles;
}

