package com.estudioveneto.landing_api.lead.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeadRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @Email(message = "E-mail inválido") @NotBlank(message = "E-mail é obrigatório") String email
) {
}
