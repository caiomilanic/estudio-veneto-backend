package com.estudioveneto.landing_api.pricing;

import com.estudioveneto.landing_api.pricing.dto.UnitDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public List<UnitDTO> getUnits() {
        return unitService.getAllUnits();
    }
}
