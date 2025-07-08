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
public class FavortiosController {

    @Autowired
    private usuariosRepository usuariosRepository;

    @Autowired
    private planesRepository planesRepository;

    /**
     * Endpoint POST para añadir un plan a la lista de favoritos del usuario.
     * 
     * URL: /api/favoritos/agregar
     * Método: POST
     * Cuerpo: JSON con idUsuario y idPlan
     * 
     * @param request Contiene idUsuario e idPlan a vincular.
     * @return 200 OK si se agregó correctamente; 404 si no se encuentra alguno.
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

        usuario.getPlanes().add(plan);
        usuariosRepository.save(usuario); // JPA persistirá la relación en la tabla intermedia

        return ResponseEntity.ok("Plan añadido a favoritos correctamente");
    }

    // DTO para recibir datos del frontend
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
     * Endpoint DELETE para eliminar un plan de la lista de favoritos del usuario.
     * 
     * URL: /api/favoritos/eliminar
     * Método: DELETE
     * Cuerpo: JSON con idUsuario e idPlan
     * 
     * @param request Contiene idUsuario e idPlan a desvincular.
     * @return 200 OK si se eliminó correctamente; 404 si no se encuentra alguno.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarFavorito(@RequestBody FavoritoRequest request) {
        Optional<Usuarios> optionalUsuario = usuariosRepository.findById(request.getIdUsuario());
        Optional<planes> optionalPlan = planesRepository.findById(request.getIdPlan());

        if (optionalUsuario.isEmpty() || optionalPlan.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario o Plan no encontrado");
        }

        Usuarios usuario = optionalUsuario.get();
        planes plan = optionalPlan.get();

        if (usuario.getPlanes().contains(plan)) {
            usuario.getPlanes().remove(plan);
            usuariosRepository.save(usuario); // Se actualiza la relación en la BD
            return ResponseEntity.ok("Plan eliminado de favoritos correctamente");
        } else {
            return ResponseEntity.status(400).body("El plan no está en favoritos del usuario");
        }
    }

    /**
     * Endpoint GET para obtener la lista de planes favoritos de un usuario.
     * 
     * URL: /api/favoritos/{idUsuario}
     * Método: GET
     * 
     * @param idUsuario ID del usuario cuyos planes favoritos se quieren obtener.
     * @return Lista de planes favoritos o 404 si el usuario no existe.
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> obtenerFavoritos(@PathVariable Long idUsuario) {
        Optional<Usuarios> optionalUsuario = usuariosRepository.findById(idUsuario);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        Usuarios usuario = optionalUsuario.get();

        // Retorna el set de planes favoritos directamente
        return ResponseEntity.ok(usuario.getPlanes());
    }

}
