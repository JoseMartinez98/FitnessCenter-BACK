package com.macaelfitnesscenterback.fitnesscenterback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macaelfitnesscenterback.fitnesscenterback.model.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    
}
