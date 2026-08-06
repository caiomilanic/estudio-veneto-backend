package com.estudioveneto.landing_api.content;

import com.estudioveneto.landing_api.content.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByOrderByDisplayOrderAsc();
}
