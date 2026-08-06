package com.estudioveneto.landing_api.content;

import com.estudioveneto.landing_api.content.entity.SiteContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<SiteContent, Long> {
}
