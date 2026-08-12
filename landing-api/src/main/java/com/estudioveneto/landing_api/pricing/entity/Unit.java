package com.estudioveneto.landing_api.pricing.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "units")
@Data
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // "Studio" ou "Studio Garden"

    @Column(name = "area_privativa", nullable = false)
    private String areaPrivativa; // "18,33m² a 19,39m²"

    @Column(name = "area_total", nullable = false)
    private String areaTotal; // "24,39m² a 25,80m²"

    @Column(name = "area_jardim")
    private String areaJardim; // "14,52m² a 25,98m²" — só preenchido no Garden, nullable

    @Column(name = "preco_a_partir_de", nullable = false)
    private BigDecimal precoAPartirDe;

    @Column(name = "display_order")
    private Integer displayOrder;
}