package com.estudioveneto.landing_api.pricing;

import com.estudioveneto.landing_api.pricing.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findAllByOrderByDisplayOrderAsc();
}
