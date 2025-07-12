package com.macaelfitnesscenterback.fitnesscenterback.repository;

import com.macaelfitnesscenterback.fitnesscenterback.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
        List<Noticia> findAllByOrderByFechaRegistroDesc();
}
