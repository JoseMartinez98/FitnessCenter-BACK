package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.model.Rutina;
import com.macaelfitnesscenterback.fitnesscenterback.repository.RutinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.nio.file.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class RutinaController {

    @Autowired
    private RutinaRepository rutinaRepository;

    @GetMapping("/rutinas")
    public Page<Rutina> getRutinas(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rutinaRepository.findAll(pageable);
    }

    @DeleteMapping("/rutinas/{id}")
    public void deleteRutina(@PathVariable Long id) {
        rutinaRepository.deleteById(id);
    }

    @PostMapping("/upload-rutina")
    public ResponseEntity<String> uploadRutina(@RequestParam("file") MultipartFile file) {
        try {
            String nombre = StringUtils.cleanPath(file.getOriginalFilename());
            Path ruta = Paths.get("uploads/documents/rutinas/").resolve(nombre);
            Files.createDirectories(ruta.getParent());
            Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            String url = "/documents/rutinas/" + nombre;
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al subir el PDF");
        }
    }

    @PostMapping("/subirRutina")
    public ResponseEntity<Rutina> crearRutina(@RequestBody Rutina rutina) {
        Rutina nueva = rutinaRepository.save(rutina);
        return ResponseEntity.ok(nueva);
    }
}
