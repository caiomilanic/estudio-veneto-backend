package com.estudioveneto.landing_api.pricing;

import com.estudioveneto.landing_api.pricing.dto.UnitDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<UnitDTO> getAllUnits() {
        return unitRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(UnitDTO::from)
                .toList();
    }
}