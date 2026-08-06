package com.estudioveneto.landing_api.content;

import com.estudioveneto.landing_api.content.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {
}