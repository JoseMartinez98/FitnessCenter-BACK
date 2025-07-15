package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.model.Noticia;
import com.macaelfitnesscenterback.fitnesscenterback.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Controlador REST para gestionar operaciones relacionadas con las noticias.
 * 
 * Este controlador expone un endpoint público para obtener el listado de
 * noticias disponibles.
 */
@RestController
@RequestMapping("/api")
public class NoticiaController {

    // Inyección del repositorio que accede a la base de datos de noticias
    @Autowired
    private NoticiaRepository noticiaRepository;

    /**
     * Endpoint GET para obtener todas las noticias registradas en el sistema.
     *
     * URL: /api/noticias
     * Método: GET
     * Acceso: Público
     *
     * @return Lista de objetos Noticia obtenida desde la base de datos.
     */
    @GetMapping("/noticias")
    public Page<Noticia> getNoticias(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noticiaRepository.findAllByOrderByFechaRegistroDesc(pageable);
    }
}
