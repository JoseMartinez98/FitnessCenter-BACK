package com.macaelfitnesscenterback.fitnesscenterback.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;
import java.util.Optional;

public interface usuariosRepository extends JpaRepository<Usuarios, Long> {
    boolean existsByEmail(String email);
    Optional<Usuarios> findByEmail(String email);
}

