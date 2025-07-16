package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.model.Rutina;
import com.macaelfitnesscenterback.fitnesscenterback.repository.RutinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de rutinas de entrenamiento.
 * 
 * Proporciona un endpoint para obtener todas las rutinas disponibles.
 */
@RestController
@RequestMapping("/api")
public class RutinaController {

    // Inyección del repositorio que permite interactuar con la base de datos de
    // rutinas
    @Autowired
    private RutinaRepository rutinaRepository;

    /**
     * Endpoint GET para recuperar todas las rutinas registradas.
     *
     * URL: /api/rutinas
     * Método: GET
     * Acceso: Público
     *
     * @return Lista completa de objetos Rutina.
     */
    @GetMapping("/rutinas")
    public List<Rutina> getRutinas() {
        return rutinaRepository.findAll(); // Obtiene todas las rutinas disponibles desde la base de datos
    }
    @DeleteMapping("/rutinas/{id}")
    public void deleteRutina(@PathVariable Long id) {
        rutinaRepository.deleteById(id);
    }
} 
