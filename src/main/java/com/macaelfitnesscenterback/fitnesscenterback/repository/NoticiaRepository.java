package com.macaelfitnesscenterback.fitnesscenterback.repository;

import com.macaelfitnesscenterback.fitnesscenterback.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
        Page<Noticia> findAllByOrderByFechaRegistroDesc(Pageable pageable);
}
