package com.estudioveneto.landing_api.lead;

import com.estudioveneto.landing_api.lead.dto.LeadRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void receberLead(@Valid @RequestBody LeadRequestDTO dto) {
        leadService.salvarLead(dto);
    }
}
