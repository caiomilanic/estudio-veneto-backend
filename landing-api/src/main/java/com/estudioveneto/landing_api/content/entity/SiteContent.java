package com.estudioveneto.landing_api.content.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "site_content")
@Data
public class SiteContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_key", nullable = false, unique = true)
    private String sectionKey; // ex: "sobre", "missao", "titulo_principal"

    @Column(columnDefinition = "TEXT")
    private String text;
}