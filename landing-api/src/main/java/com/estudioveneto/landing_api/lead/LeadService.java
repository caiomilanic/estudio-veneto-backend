package com.estudioveneto.landing_api.lead;

import com.estudioveneto.landing_api.lead.dto.LeadRequestDTO;
import com.estudioveneto.landing_api.lead.entity.Lead;
import org.springframework.stereotype.Service;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    private final BrevoEmailService brevoEmailService;

    public LeadService(LeadRepository leadRepository, BrevoEmailService brevoEmailService) {
        this.leadRepository = leadRepository;
        this.brevoEmailService = brevoEmailService;
    }

    public Lead salvarLead(LeadRequestDTO dto) {
        Lead lead = new Lead();
        lead.setNome(dto.nome());
        lead.setTelefone(dto.telefone());
        lead.setEmail(dto.email());
        lead.setPreferenciaContato(dto.preferenciaContato());

        Lead salvo = leadRepository.save(lead);
        brevoEmailService.enviarNotificacaoLead(salvo);
        return salvo;
    }
}