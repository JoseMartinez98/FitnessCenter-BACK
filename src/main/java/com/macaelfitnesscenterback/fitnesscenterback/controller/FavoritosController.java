package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;
import com.macaelfitnesscenterback.fitnesscenterback.model.planes;
import com.macaelfitnesscenterback.fitnesscenterback.repository.planesRepository;
import com.macaelfitnesscenterback.fitnesscenterback.repository.usuariosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador para gestionar los planes favoritos de los usuarios.
 */
@RestController
@RequestMapping("/api/favoritos")
public class FavoritosController {

    @Autowired
    private usuariosRepository usuariosRepository;

    @Autowired
    private planesRepository planesRepository;

    // DTO para request con idUsuario e idPlan
    public static class FavoritoRequest {
        private Long idUsuario;
        private Long idPlan;

        public Long getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
        }

        public Long getIdPlan() {
            return idPlan;
        }

        public void setIdPlan(Long idPlan) {
            this.idPlan = idPlan;
        }
    }

    /**
     * Asignar un plan favorito (un solo plan) al usuario.
     */
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarFavorito(@RequestBody FavoritoRequest request) {
        Optional<Usuarios> optionalUsuario = usuariosRepository.findById(request.getIdUsuario());
        Optional<planes> optionalPlan = planesRepository.findById(request.getIdPlan());

        if (optionalUsuario.isEmpty() || optionalPlan.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario o Plan no encontrado");
        }

        Usuarios usuario = optionalUsuario.get();
        planes plan = optionalPlan.get();

        usuario.setPlan(plan);  // asigna el plan favorito (solo uno)
        usuariosRepository.save(usuario);

        return ResponseEntity.ok("Plan asignado como favorito correctamente");
    }

    /**
     * Eliminar plan favorito del usuario (establecer a null).
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarFavorito(@RequestBody FavoritoRequest request) {
        Optional<Usuarios> optionalUsuario = usuariosRepository.findById(request.getIdUsuario());

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        Usuarios usuario = optionalUsuario.get();

        if (usuario.getPlan() != null && usuario.getPlan().getId().equals(request.getIdPlan())) {
            usuario.setPlan(null); // elimina el plan favorito
            usuariosRepository.save(usuario);
            return ResponseEntity.ok("Plan favorito eliminado correctamente");
        } else {
            return ResponseEntity.status(400).body("El usuario no tiene asignado ese plan como favorito");
        }
    }

    /**
     * Obtener el plan favorito de un usuario (puede ser null).
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> obtenerFavorito(@PathVariable Long idUsuario) {
        Optional<Usuarios> optionalUsuario = usuariosRepository.findById(idUsuario);

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        Usuarios usuario = optionalUsuario.get();

        planes planFavorito = usuario.getPlan();

        if (planFavorito == null) {
            return ResponseEntity.ok("El usuario no tiene plan favorito asignado");
        } else {
            return ResponseEntity.ok(planFavorito);
        }
    }
}
