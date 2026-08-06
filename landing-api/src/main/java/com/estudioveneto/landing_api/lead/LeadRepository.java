package com.estudioveneto.landing_api.lead;

import com.estudioveneto.landing_api.lead.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
}