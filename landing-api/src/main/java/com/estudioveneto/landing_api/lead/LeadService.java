package com.estudioveneto.landing_api.lead;

import com.estudioveneto.landing_api.lead.dto.LeadRequestDTO;
import com.estudioveneto.landing_api.lead.entity.Lead;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    private final JavaMailSender mailSender;

    @Value("${leads.notification.to}")
    private String corretorEmail;

    @Value("${leads.notification.from}")
    private String remetente;

    public LeadService(LeadRepository leadRepository, JavaMailSender mailSender) {
        this.leadRepository = leadRepository;
        this.mailSender = mailSender;
    }

    public Lead salvarLead(LeadRequestDTO dto) {
        Lead lead = new Lead();
        lead.setNome(dto.nome());
        lead.setTelefone(dto.telefone());
        lead.setEmail(dto.email());

        Lead salvo = leadRepository.save(lead);
        enviarNotificacao(salvo);
        return salvo;
    }

    private void enviarNotificacao(Lead lead) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(corretorEmail);
            message.setSubject("Novo lead - Studios Veneto");
            message.setText("""
                    Novo lead recebido pela landing page:

                    Nome: %s
                    Telefone: %s
                    E-mail: %s
                    """.formatted(lead.getNome(), lead.getTelefone(), lead.getEmail()));

            mailSender.send(message);
        } catch (Exception e) {
            // não deixa a falha de e-mail quebrar o salvamento do lead
            System.err.println("Falha ao enviar e-mail de notificação: " + e.getMessage());
        }
    }
}