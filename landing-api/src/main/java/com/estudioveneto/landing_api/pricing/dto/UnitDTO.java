package com.estudioveneto.landing_api.pricing.dto;

import com.estudioveneto.landing_api.pricing.entity.Unit;
import java.math.BigDecimal;

public record UnitDTO(String tipo, Double metragem, BigDecimal precoAPartirDe) {
    public static UnitDTO from(Unit entity) {
        return new UnitDTO(entity.getTipo(), entity.getMetragem(), entity.getPrecoAPartirDe());
    }
}
