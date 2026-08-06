package com.estudioveneto.landing_api.lead.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LeadRequestDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Telefone é obrigatório") String telefone,
        @Email(message = "E-mail inválido") @NotBlank(message = "E-mail é obrigatório") String email,
        @NotBlank(message = "Preferência de contato é obrigatória")
        @Pattern(regexp = "email|whatsapp|ligacao", message = "Preferência de contato inválida")
        String preferenciaContato
) {
}