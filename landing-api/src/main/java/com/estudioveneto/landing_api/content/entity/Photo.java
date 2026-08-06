package com.estudioveneto.landing_api.content.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "photos")
@Data
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url; // link do Cloudinary

    private String caption;

    @Column(name = "display_order")
    private Integer displayOrder;
}