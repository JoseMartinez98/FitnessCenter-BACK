package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.DTO.PlanDTO;
import com.macaelfitnesscenterback.fitnesscenterback.repository.planesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar operaciones relacionadas con los planes de
 * entrenamiento o membresías.
 * 
 * Expone un endpoint público para obtener todos los planes registrados en el
 * sistema.
 */
@RestController
@RequestMapping("/api/planes")
public class planesController {

    // Inyección del repositorio de planes para acceder a la base de datos
    @Autowired
    private planesRepository planesRepository;

    /**
     * Endpoint GET para obtener la lista completa de planes disponibles.
     *
     * URL: /api/planes
     * Método: GET
     * Acceso: Público
     *
     * @return Lista de objetos tipo 'planes' almacenados en la base de datos.
     */
    @GetMapping
    public List<PlanDTO> getAllPlanes() {
        return planesRepository.findAll().stream()
                .map(plan -> new PlanDTO(
                        plan.getId(),
                        plan.getNombre(),
                        plan.getPrecioMensual(),
                        plan.getPrecioDiario(),
                        plan.getDescuento(),
                        plan.getPrecioConDescuento(),
                        plan.getImagen()))
                .collect(Collectors.toList());
    }
}
