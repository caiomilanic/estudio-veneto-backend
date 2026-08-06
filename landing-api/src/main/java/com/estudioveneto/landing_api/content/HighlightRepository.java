package com.estudioveneto.landing_api.content;

import com.estudioveneto.landing_api.content.entity.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {
    List<Highlight> findByCategoryOrderByDisplayOrderAsc(String category);
}
