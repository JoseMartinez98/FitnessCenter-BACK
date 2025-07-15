package com.macaelfitnesscenterback.fitnesscenterback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.macaelfitnesscenterback.fitnesscenterback.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
