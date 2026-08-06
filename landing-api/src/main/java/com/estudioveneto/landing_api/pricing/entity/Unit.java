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

    @Column(name = "metragem", nullable = false)
    private Double metragem; // 18.0 ou 19.0

    @Column(name = "preco_a_partir_de", nullable = false)
    private BigDecimal precoAPartirDe; // 199900.00 / 219900.00

    @Column(name = "display_order")
    private Integer displayOrder;
}
