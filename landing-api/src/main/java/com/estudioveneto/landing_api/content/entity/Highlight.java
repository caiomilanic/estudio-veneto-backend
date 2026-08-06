package com.estudioveneto.landing_api.content.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "highlights")
@Data
public class Highlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category; // "localizacao" ou "diferenciais"

    @Column(nullable = false)
    private String text; // ex: "Terminal Santa Cândida" ou "Possibilidade de financiamento bancário"

    @Column(name = "display_order")
    private Integer displayOrder;
}