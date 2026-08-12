package com.estudioveneto.landing_api.pricing.dto;

import com.estudioveneto.landing_api.pricing.entity.Unit;
import java.math.BigDecimal;

public record UnitDTO(
        String tipo,
        String areaPrivativa,
        String areaTotal,
        String areaJardim,
        BigDecimal precoAPartirDe
) {
    public static UnitDTO from(Unit entity) {
        return new UnitDTO(
                entity.getTipo(),
                entity.getAreaPrivativa(),
                entity.getAreaTotal(),
                entity.getAreaJardim(),
                entity.getPrecoAPartirDe()
        );
    }
}