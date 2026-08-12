package com.estudioveneto.landing_api.lead;

import com.estudioveneto.landing_api.lead.entity.Lead;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrevoEmailService {

    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${brevo.api-key}")
    private String apiKey;

    @Value("${brevo.sender-email}")
    private String remetente;

    @Value("${leads.notification.to}")
    private String corretorEmail;

    @Async
    public void enviarNotificacaoLead(Lead lead) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> sender = new HashMap<>();
            sender.put("name", "Studios Veneto");
            sender.put("email", remetente);

            Map<String, String> destinatario = new HashMap<>();
            destinatario.put("email", corretorEmail);

            String texto = """
                    Novo lead recebido pela landing page:

                    Nome: %s
                    Telefone: %s
                    E-mail: %s
                    Prefere contato via: %s
                    """.formatted(lead.getNome(), lead.getTelefone(), lead.getEmail(), lead.getPreferenciaContato());

            Map<String, Object> body = new HashMap<>();
            body.put("sender", sender);
            body.put("to", List.of(destinatario));
            body.put("subject", "Novo lead - Studios Veneto");
            body.put("textContent", texto);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(BREVO_API_URL, request, String.class);

        } catch (Exception e) {
            System.err.println("Falha ao enviar e-mail via Brevo API: " + e.getMessage());
        }
    }
}