package com.estudioveneto.landing_api.content.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "social_links")
@Data
public class SocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "whatsapp_number")
    private String whatsappNumber;

    @Column(name = "whatsapp_message")
    private String whatsappMessage;
}